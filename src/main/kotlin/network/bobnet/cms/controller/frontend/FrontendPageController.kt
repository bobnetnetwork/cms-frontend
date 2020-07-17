package network.bobnet.cms.controller.frontend

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.service.content.PageService
import org.apache.commons.text.StringEscapeUtils
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

@Controller
class FrontendPageController(
        private val displayLanguageController: DisplayLanguageController,
        private val pageService: PageService,
        private val sidebar: SidebarController) {

    @GetMapping("/{slug}")
    fun page(@PathVariable slug: String, model: Model): String {
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        model.addAttribute(sidebar.addSidebar(model))

        val page = pageService.findBySlug(slug).render()?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This page does not exist")

        model["title"] = page.title
        page.content = StringEscapeUtils.unescapeHtml4(page.content)
        model["page"] = page
        model["featuredImage"] = page.featuredImage

        return "frontend/page"
    }
}