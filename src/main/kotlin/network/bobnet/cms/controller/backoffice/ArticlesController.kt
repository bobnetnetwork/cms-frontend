package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.model.content.Article
import network.bobnet.cms.model.content.Category
import network.bobnet.cms.model.user.User
import network.bobnet.cms.repository.content.ArticleRepository
import network.bobnet.cms.repository.content.CategoryRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Controller
class ArticlesController (
                    private val articleRepository: ArticleRepository,
                    private val displayLanguageController: DisplayLanguageController,
                    private val categoryRepository: CategoryRepository) {

    @GetMapping("/admin/articles")
    fun edit(model: Model): String{
        model.addAttribute(displayLanguageController.getArticlesLabels(model))
        model["articles"] = articleRepository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "backoffice/articles"
    }

    @GetMapping("/admin/articles/{slug}")
    fun article(@PathVariable slug: String, model: Model): String{
        model.addAttribute(displayLanguageController.getArticleEditorLabels(model))
        val article = if(slug.equals("new")){
            RenderedArticle("", "", "", "", "", User("", ""), LocalDateTime.now(), 0)
        }else{
            articleRepository
                    .findBySlug(slug)
                    ?.render()
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
        }
        model["articletitle"] = article.title
        model["articlecontent"] = article.content
        model["author"] = article.author
        model["featuredimage"] = article.featuredImage
        model["categories"] = categoryRepository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "backoffice/article"
    }

    fun Article.render() = id?.let {
        RenderedArticle(
                slug,
                title,
                headline,
                content,
                featuredImage,
                author,
                addedAt,
                it

        )
    }

    data class RenderedArticle(
            val slug: String,
            val title: String,
            val headline: String,
            val content: String,
            val featuredImage: String,
            val author: User,
            val addedAt: LocalDateTime,
            val id: Long)

    fun Category.render() = RenderCategory(
            slug,
            name,
            featuredImage,
            description,
            id
    )

    data class RenderCategory(
            val slug: String,
            val name: String,
            val featuredImage: String,
            val description: String,
            val id: Long?)


}
