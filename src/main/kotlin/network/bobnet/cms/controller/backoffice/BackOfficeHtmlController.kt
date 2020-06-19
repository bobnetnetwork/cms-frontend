package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.repository.user.UserRepository
import network.bobnet.cms.util.LoggedInUser
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import javax.servlet.http.HttpServletResponse

@Controller
class BackOfficeHtmlController (private val displayLanguageController: DisplayLanguageController,
                                private val userRepository: UserRepository){

    @GetMapping("/admin")
    fun admin(response: HttpServletResponse, model: Model): String {
        LoggedInUser(userRepository).setUser(response)
        model.addAttribute(displayLanguageController.getDashboardLabels(model))
        return "backoffice/admin"
    }

    @GetMapping("/admin/profile")
    fun profile(model: Model): String{

        return "profile"
    }

    @GetMapping("/admin/users/{username}")
    fun editUser(@PathVariable username: String, model: Model): String{

        return "edituser"
    }


}