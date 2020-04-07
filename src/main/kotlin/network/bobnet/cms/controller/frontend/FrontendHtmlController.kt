package network.bobnet.cms.controller.frontend

import network.bobnet.cms.model.content.Article
import network.bobnet.cms.BlogProperties
import network.bobnet.cms.controller.SiteInfoController
import network.bobnet.cms.model.content.Category
import network.bobnet.cms.model.user.User
import network.bobnet.cms.repository.content.ArticleRepository
import network.bobnet.cms.repository.content.CategoryRepository
import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Controller
class FrontendHtmlController(private val repository: ArticleRepository,
                             private val properties: BlogProperties,
                             private val categoriRepository: CategoryRepository,
                             private val siteInfoController: SiteInfoController) {

    @GetMapping("/")
    fun blog(model: Model): String {
        model.addAttribute(siteInfoController.getSiteInfo(model))
        model["title"] = properties.title
        model["banner"] = properties.banner
        model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "frontend/blog"
    }

    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        model.addAttribute(siteInfoController.getSiteInfo(model))
        val article = repository
                .findBySlug(slug)
                ?.render()
                ?: throw ResponseStatusException(NOT_FOUND, "This article does not exist")
        model["title"] = article.title
        model["article"] = article
        model["featuredImage"] = article.featuredImage
        return "frontend/article"
    }

    @GetMapping("/categories/{slug]")
    fun category(@PathVariable slug: String, model: Model): String {
        model.addAttribute(siteInfoController.getSiteInfo(model))
        val category = categoriRepository
                .findBySlug(slug)
                ?.render()
                ?: throw ResponseStatusException(NOT_FOUND, "This article does not exist")
        model["ctageoryname"] = category.name
        model["featuredimage"] = category.featuredImage
        model["categorydescription"] = category.description
        model["articles"] = category.id?.let { repository.findByCategoryIds(it).map { it.render() } }!!
        return "frontend/category"
    }

    @GetMapping("/categories")
    fun categories(model: Model): String{
        model.addAttribute(siteInfoController.getSiteInfo(model))
        model["categories"] = categoriRepository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "frontend/categories"
    }

    fun Article.render() = RenderedArticle(
            slug,
            title,
            headline,
            content,
            featuredImage,
            author,
            addedAt

    )

    data class RenderedArticle(
            val slug: String,
            val title: String,
            val headline: String,
            val content: String,
            val featuredImage: String,
            val author: User,
            val addedAt: LocalDateTime)

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
