package network.bobnet.cms.controller

import network.bobnet.cms.model.Article
import network.bobnet.cms.BlogProperties
import network.bobnet.cms.model.Options
import network.bobnet.cms.model.User
import network.bobnet.cms.repository.ArticleRepository
import network.bobnet.cms.repository.OptionsRepository
import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Controller
class HtmlController(private val repository: ArticleRepository,
                     private val optionsRepository: OptionsRepository,
                     private val properties: BlogProperties) {

    fun getSiteInfo(model: Model): Model {
        val names = mutableListOf<String>()
        names.add("sitename")
        names.add("sitedescription")
        names.add("home")
        for (name in names) {
            val options = optionsRepository
                    .findByName(name)
                    ?.render()
                    ?: throw ResponseStatusException(NOT_FOUND, "This options does not exist")
            model[name] = options.value
        }
        return model
    }

    @GetMapping("/")
    fun blog(model: Model): String {
        model.addAttribute(getSiteInfo(model))
        model["title"] = properties.title
        model["banner"] = properties.banner
        model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "blog"
    }

    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        model.addAttribute(getSiteInfo(model))
        val article = repository
                .findBySlug(slug)
                ?.render()
                ?: throw ResponseStatusException(NOT_FOUND, "This article does not exist")
        model["title"] = article.title
        model["article"] = article
        model["featuredImage"] = article.featuredImage
        return "article"
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

    fun Options.render() = RenderOptions(
            name,
            value)

    data class RenderOptions(
            val name: String,
            val value: String)

}
