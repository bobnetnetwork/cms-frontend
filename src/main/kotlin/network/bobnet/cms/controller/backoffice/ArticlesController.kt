package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.model.content.Article
import network.bobnet.cms.model.content.Tag
import network.bobnet.cms.repository.content.CategoryRepository
import network.bobnet.cms.repository.content.TagRepository
import network.bobnet.cms.repository.user.UserRepository
import network.bobnet.cms.service.ArticleService
import network.bobnet.cms.service.LogService
import network.bobnet.cms.util.Extensions
import network.bobnet.cms.util.LoggedInUser
import org.apache.commons.text.StringEscapeUtils
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*


@Controller
class ArticlesController (
                    private val displayLanguageController: DisplayLanguageController,
                    private val categoryRepository: CategoryRepository,
                    private val articleService: ArticleService,
                    private val userRepository: UserRepository,
                    private val tagRepository: TagRepository) {

    private val logger: LogService = LogService(this.javaClass)

    private final val ROW_PER_PAGE: Int = 5

    private final val ARTICLES_TEMPLATE = "backoffice/articles"
    private final val ARTICLE_TEMPLATE = "backoffice/article"

    private val extensions = Extensions()

    @GetMapping("/admin/articles")
    fun articles(model: Model, @RequestParam(value = "page", defaultValue = "1") pageNumber: Int): String{
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
    fun showEditArticle(@PathVariable slug: String, model: Model): String{
        model.addAttribute(displayLanguageController.getArticleEditorLabels(model))
        return try{
            val article = articleService.findBySlug(slug)
            article.content = StringEscapeUtils.unescapeHtml4(article.content)
            model["add"] = false
            model["article"] = article
            //model["categories"] = categoryRepository.findAllByOrderByAddedAtDesc().map { it.render() }
            if(article.tags?.isNotEmpty()!!){
                val tagsIterator = article.tags!!.iterator()
                var first = true
                val tagsList = StringBuilder()
                while(tagsIterator.hasNext()){
                    if(first){
                        tagsList.append(tagsIterator.next().title)
                        first = false
                    }else{
                        tagsList.append(", ").append(tagsIterator.next().title)
                    }
                }
                model["tagsList"] = tagsList.toString()
            }else {
                model["tagsList"] = ""
            }

            ARTICLE_TEMPLATE
        }catch(ex: Exception){
            model["errorMessage"] = ex.stackTrace.toString()
            logger.error(ex.stackTrace.toString())
            ARTICLE_TEMPLATE
        }

    }



    @PostMapping("/admin/articles/{slug}")
    fun editArticle(@PathVariable slug: String, model: Model, @RequestParam queryMap: Map<String, String>): String{
        model.addAttribute(displayLanguageController.getArticleEditorLabels(model))
        return try{
            val article = articleService.findBySlug(slug)
            article.content = StringEscapeUtils.escapeHtml4(queryMap["content"].toString())
            val tags = queryMap["tags"].toString().split(",").toTypedArray()
            if(tags.isNotEmpty()){
                val tagIterator = tags.iterator()

                while(tagIterator.hasNext()){
                    val tag = tagIterator.next().replace("\\s".toRegex(), "")
                    if(tagRepository.countByTitle(tag) > 0 ){
                        if(!article.tags?.contains(tagRepository.findByTitle(tag))!!){
                            article.tags!!.add(tagRepository.findByTitle(tag))
                        }
                    }else{
                        val newTag = Tag()
                        newTag.title = tag
                        val extensions = Extensions()
                        newTag.slug = extensions.slugify(tag)
                        tagRepository.save(newTag)
                        article.tags!!.add(newTag)
                    }
                }
            }


            articleService.update(article)
            "redirect:/admin/articles/" + article.slug
        }catch(ex: Exception){
            model["errorMessage"] = ex.stackTrace.toString()
            logger.error(ex.stackTrace.toString())
            model["add"] = false
            ARTICLE_TEMPLATE
        }
    }

    @GetMapping("/admin/articles/new")
    fun showAddArticle(model: Model): String{
        model.addAttribute(displayLanguageController.getArticleEditorLabels(model))
        val article = Article()
        model["add"] = true
        model["article"] = article

        return ARTICLE_TEMPLATE
    }

    @PostMapping("/admin/articles/new")
    fun addArticle(model: Model, @ModelAttribute("article") article: Article): String{
        model.addAttribute(displayLanguageController.getArticleEditorLabels(model))
        return try{
            article.author = LoggedInUser(userRepository).getUser()!!
            article.slug = extensions.slugify(article.title)
            article.content = StringEscapeUtils.escapeHtml4(article.content)
            val newArticle: Article = articleService.save(article)
            "redirect:/admin/articles/" + newArticle.slug
        }catch (ex: Exception){
            model["errorMessage"] = ex.stackTrace.toString()
            logger.error(ex.stackTrace.toString())
            model["add"] = true
            ARTICLE_TEMPLATE
        }
    }
}
