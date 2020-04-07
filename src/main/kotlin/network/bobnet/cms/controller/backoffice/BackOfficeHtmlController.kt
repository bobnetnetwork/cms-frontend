package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.controller.SiteInfoController
import network.bobnet.cms.model.content.Article
import network.bobnet.cms.model.user.User
import network.bobnet.cms.repository.content.ArticleRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Controller
class BackOfficeHtmlController (private val repository: ArticleRepository,
                                private val siteInfoController: SiteInfoController){

    @GetMapping("/admin")
    fun admin(model: Model): String {
        model.addAttribute(siteInfoController.getSiteInfo(model))
        return "backoffice/admin"
    }

    @GetMapping("/admin/edit/{slug}")
    fun edit(@PathVariable slug: String, model: Model): String{

        return "edit"
    }

    @GetMapping("/admin/articles")
    fun edit(model: Model): String{
        model.addAttribute(siteInfoController.getSiteInfo(model))
        model["title"] = "Articles"
        model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "backoffice/articles"
    }

    @GetMapping("/admin/articles/{slug}")
    fun article(@PathVariable slug: String, model: Model): String{
        model.addAttribute(siteInfoController.getSiteInfo(model))
        val article = repository
                .findBySlug(slug)
                ?.render()
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
        model["title"] = "Edit Article"
        model["articletitle"] = article.title
        model["articlecontent"] = article.content
        model["author"] = article.author
        model["featuredimage"] = article.featuredImage
        return "backoffice/article"
    }

    @GetMapping("/admin/profile")
    fun profile(model: Model): String{

        return "profile"
    }

    @GetMapping("/admin/users")
    fun users(model: Model): String{

        return "users"
    }

    @GetMapping("/admin/users/{username}")
    fun editUser(@PathVariable username: String, model: Model): String{

        return "edituser"
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
}