package network.bobnet.cms.controller.backend

import network.bobnet.cms.controller.SiteInfoController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class BackendHtmlController (private val siteInfoController: SiteInfoController){

    @GetMapping("/admin")
    fun admin(model: Model): String {
        model.addAttribute(siteInfoController.getSiteInfo(model))
        return "admin"
    }

    @GetMapping("/admin/edit/{slug}")
    fun edit(@PathVariable slug: String, model: Model): String{

        return "edit"
    }
}