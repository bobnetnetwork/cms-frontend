package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.controller.SiteInfoController
import network.bobnet.cms.model.content.Article
import network.bobnet.cms.model.user.User
import network.bobnet.cms.repository.content.ArticleRepository
import network.bobnet.cms.util.Translator
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.*

@Controller
class BackOfficeHtmlController (private val repository: ArticleRepository,
                                private val siteInfoController: SiteInfoController){

    fun setLanguage() {
        LocaleContextHolder.setLocale(Locale(siteInfoController.getSiteLanguage()))
    }

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
        LocaleContextHolder.setLocale(Locale(model.getAttribute("sitelanguage") as String?))
        val article = repository
                .findBySlug(slug)
                ?.render()
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
        model["articletitle"] = article.title
        model["articlecontent"] = article.content
        model["author"] = article.author
        model["featuredimage"] = article.featuredImage
        model["lang.delete"] = Translator.toLocale("lang.delete")
        model["lang.title"] = Translator.toLocale("lang.title")
        model["lang.content"] = Translator.toLocale("lang.content")
        model["lang.saveAsDraft"] = Translator.toLocale("lang.saveAsDraft")
        model["lang.general"] = Translator.toLocale("lang.general")
        model["lang.publish"] = Translator.toLocale("lang.publish")
        model["lang.featuredImage"] = Translator.toLocale("lang.featuredImage")
        model["lang.categories"] = Translator.toLocale("lang.categories")
        model["lang.tags"] = Translator.toLocale("lang.tags")
        model["lang.author"] = Translator.toLocale("lang.author")
        model["lang.excerpt"] = Translator.toLocale("lang.excerpt")
        model["lang.editArticle"] = Translator.toLocale("lang.editArticle")
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