package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.controller.DisplayLanguageController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@Controller
class BackOfficeHtmlController (private val displayLanguageController: DisplayLanguageController){

    @GetMapping("/admin")
    fun admin(model: Model): String {
        model.addAttribute(displayLanguageController.getDashboardLabels(model))
        return "backoffice/admin"
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


}