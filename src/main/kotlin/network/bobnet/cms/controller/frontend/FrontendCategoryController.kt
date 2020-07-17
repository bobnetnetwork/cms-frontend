package network.bobnet.cms.controller.frontend

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.repository.content.CategoryRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

@Controller
class FrontendCategoryController(
        private val displayLanguageController: DisplayLanguageController,
        private val categoryRepository: CategoryRepository,
        private val sidebar: SidebarController) {

    @GetMapping("/categories/{slug}")
    fun category(@PathVariable slug: String, model: Model): String {
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        model.addAttribute(sidebar.addSidebar(model))
        val category = categoryRepository
                .findBySlug(slug)
                ?.render()
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
        model["ctageoryname"] = category.name
        model["featuredimage"] = category.featuredImage
        model["categorydescription"] = category.description
        //model["articles"] = category.id?.let { it -> repository.findByCategoryIds(it).map { it.render() } }!!
        return "frontend/category"
    }

    @GetMapping("/categories")
    fun categories(model: Model): String {
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        model.addAttribute(sidebar.addSidebar(model))
        model["categories"] = categoryRepository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "frontend/categories"
    }
}
