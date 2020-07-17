package network.bobnet.cms.controller.backoffice.content

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.model.content.Article
import network.bobnet.cms.model.content.Tag
import network.bobnet.cms.repository.administration.UserRepository
import network.bobnet.cms.service.administration.LogService
import network.bobnet.cms.service.content.ArticleService
import network.bobnet.cms.service.content.TagService
import network.bobnet.cms.util.Extensions
import network.bobnet.cms.util.LoggedInUser
import org.apache.commons.text.StringEscapeUtils
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class BackOfficeArticleController(
        private val displayLanguageController: DisplayLanguageController,
        private val articleService: ArticleService,
        private val userRepository: UserRepository,
        private val tagService: TagService) {

    private val logger: LogService = LogService(this.javaClass)

    private final val ROW_PER_PAGE: Int = 5

    private final val ARTICLES_TEMPLATE = "backoffice/content/articles"
    private final val ARTICLE_TEMPLATE = "backoffice/content/article"

    private val extensions = Extensions()

    @GetMapping("/admin/articles")
    fun articles(model: Model, @RequestParam(value = "page", defaultValue = "1") pageNumber: Int): String {
        val articles = articleService.findAll(pageNumber, ROW_PER_PAGE)
        val count = articleService.count()
        val hasPrev: Boolean = pageNumber > 1
        val hasNext: Boolean = pageNumber * ROW_PER_PAGE < count

        model.addAttribute(displayLanguageController.getArticlesLabels(model))
        model["articles"] = articles
        model["hasPrev"] = hasPrev
        model["prev"] = pageNumber - 1
        model["hasNext"] = hasNext
        model["next"] = pageNumber + 1

        return ARTICLES_TEMPLATE
    }

    @GetMapping("/admin/articles/{slug}")
    fun showEditArticle(@PathVariable slug: String, model: Model): String {
        model.addAttribute(displayLanguageController.getArticleEditorLabels(model))

            val article = articleService.findBySlug(slug)
            article.content = StringEscapeUtils.unescapeHtml4(article.content)
            model["add"] = false
            model["article"] = article
            model["tagsList"] = if (article.tags?.isNotEmpty()!!) {
                val tagsIterator = article.tags!!.iterator()
                var first = true
                val tagsList = StringBuilder()
                while (tagsIterator.hasNext()) {
                    if (first) {
                        tagsList.append(tagsIterator.next().title)
                        first = false
                    } else {
                        tagsList.append(", ").append(tagsIterator.next().title)
                    }
                }
                 tagsList.toString()
            } else {
                 ""
            }

            return ARTICLE_TEMPLATE

    }

    @PostMapping("/admin/articles/{slug}")
    fun editArticle(@PathVariable slug: String, model: Model, @RequestParam queryMap: Map<String, String>): String {
        model.addAttribute(displayLanguageController.getArticleEditorLabels(model))
        return try {
            val article = articleService.findBySlug(slug)
            article.content = StringEscapeUtils.escapeHtml4(queryMap["content"].toString())
            article.tags = setTagsToArticle(queryMap["tags"].toString())

            articleService.update(article)
            setArticleToTags(article)
            "redirect:/admin/articles/" + article.slug
        } catch (ex: Exception) {
            model["errorMessage"] = ex.stackTrace.toString()
            logger.error(ex.stackTrace.toString())
            model["add"] = false
            ARTICLE_TEMPLATE
        }
    }

    @GetMapping("/admin/articles/new")
    fun showAddArticle(model: Model): String {
        model.addAttribute(displayLanguageController.getArticleEditorLabels(model))
        val article = Article()
        model["add"] = true
        model["article"] = article
        model["tagsList"] = ""

        return ARTICLE_TEMPLATE
    }

    @PostMapping("/admin/articles/new")
    fun addArticle(model: Model, @RequestParam queryMap: Map<String, String>): String {
        model.addAttribute(displayLanguageController.getArticleEditorLabels(model))
        return try {
            val article = Article()
            article.author = LoggedInUser(userRepository).getUser()!!
            article.title = queryMap["title"].toString()
            article.slug = extensions.slugify(queryMap["title"].toString())
            article.content = StringEscapeUtils.escapeHtml4(queryMap["content"].toString())
            article.tags = setTagsToArticle(queryMap["tags"].toString())
            val newArticle: Article = articleService.save(article)
            setArticleToTags(newArticle)
            "redirect:/admin/articles/" + newArticle.slug
        } catch (ex: Exception) {
            model["errorMessage"] = ex.stackTrace.toString()
            logger.error(ex.stackTrace.toString())
            model["add"] = true
            ARTICLE_TEMPLATE
        }
    }

    fun setTagsToArticle(tagsList: String): MutableSet<Tag> {
        val tagList = mutableSetOf<Tag>()
        val tags = tagsList.split(",").toTypedArray()
        if (tags.isNotEmpty()) {
            val tagIterator = tags.iterator()
            while (tagIterator.hasNext()) {
                val tag = tagIterator.next().replace("\\s".toRegex(), "")

                if (tagService.countByTitle(tag) > 0) {
                    tagList.add(tagService.findByTitle(tag))
                } else if (tag.isNotEmpty()) {
                    val newTag = Tag()
                    newTag.title = tag
                    val extensions = Extensions()
                    newTag.slug = extensions.slugify(tag)
                    tagService.save(newTag)
                    tagList.add(newTag)
                }
            }
        }
        return tagList
    }

    fun setArticleToTags(article: Article) {
        if (article.tags?.isNotEmpty()!!) {
            val tagIterator = article.tags!!.iterator()
            while (tagIterator.hasNext()) {
                val tag = tagIterator.next()
                tag.articles?.add(article)
                tagService.update(tag)
            }
        }
    }
}
