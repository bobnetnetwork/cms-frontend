package network.bobnet.cms.controller.frontend

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.service.content.ArticleService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class FrontendController(private val articleService: ArticleService,
                         private val displayLanguageController: DisplayLanguageController,
                         private val sidebar: SidebarController) {

    @GetMapping("/")
    fun blog(model: Model): String {
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        model.addAttribute(sidebar.addSidebar(model))
        model["articles"] = articleService.findAllByOrderByAddedAtDesc().map { it.render() }
        return "frontend/blog"
    }
}
