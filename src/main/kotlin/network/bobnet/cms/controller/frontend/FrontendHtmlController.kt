package network.bobnet.cms.controller.frontend

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.repository.content.ArticleRepository
import network.bobnet.cms.repository.content.CategoryRepository
import org.apache.commons.text.StringEscapeUtils
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

@Controller
class FrontendHtmlController(private val repository: ArticleRepository,
                             private val categoryRepository: CategoryRepository,
                             private val displayLanguageController: DisplayLanguageController) {

    @GetMapping("/")
    fun blog(model: Model): String {
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "frontend/blog"
    }

    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        val article = repository
                .findBySlug(slug)
                .render()
                ?: throw ResponseStatusException(NOT_FOUND, "This article does not exist")
        model["title"] = article.title
        article.content = StringEscapeUtils.unescapeHtml4(article.content)
        model["article"] = article
        model["featuredImage"] = article.featuredImage
        return "frontend/article"
    }

    @GetMapping("/categories/{slug}")
    fun category(@PathVariable slug: String, model: Model): String {
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        val category = categoryRepository
                .findBySlug(slug)
                ?.render()
                ?: throw ResponseStatusException(NOT_FOUND, "This article does not exist")
        model["ctageoryname"] = category.name
        model["featuredimage"] = category.featuredImage
        model["categorydescription"] = category.description
        //model["articles"] = category.id?.let { it -> repository.findByCategoryIds(it).map { it.render() } }!!
        return "frontend/category"
    }

    @GetMapping("/categories")
    fun categories(model: Model): String{
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        model["categories"] = categoryRepository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "frontend/categories"
    }

}
