package network.bobnet.cms.controller.frontend

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.service.TagService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class FrontendTagController(
        private val displayLanguageController: DisplayLanguageController,
        private val tagService: TagService,
        private val sidebar: SidebarController
) {

    @GetMapping("/tag/{slug}")
    fun tag(model: Model, @PathVariable slug: String): String{
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        model.addAttribute(sidebar.addSidebar(model))
        model["articles"] = tagService.findArticlesByTag(slug).map { it.render() }
        return "frontend/blog"
    }
}