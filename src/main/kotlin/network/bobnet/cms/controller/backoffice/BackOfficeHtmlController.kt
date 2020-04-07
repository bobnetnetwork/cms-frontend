package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.controller.SiteInfoController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class BackOfficeHtmlController (private val siteInfoController: SiteInfoController){

    @GetMapping("/admin")
    fun admin(model: Model): String {
        model.addAttribute(siteInfoController.getSiteInfo(model))
        return "backoffice/admin"
    }

    @GetMapping("/admin/edit/{slug}")
    fun edit(@PathVariable slug: String, model: Model): String{

        return "edit"
    }

    @GetMapping("/admin/profile")
    fun edit(model: Model): String{

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
}