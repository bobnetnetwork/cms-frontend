package network.bobnet.cms.controller.backoffice.auth


import network.bobnet.cms.controller.DisplayLanguageController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthController(private val displayLanguageController: DisplayLanguageController) {
    @GetMapping("/login")
    fun login(model: Model): String {
        model.addAttribute(displayLanguageController.getLoginPageLabels(model))
        return "backoffice/auth/login"
    }

    @GetMapping("/register")
    fun register(model: Model): String {
        model.addAttribute(displayLanguageController.getRegistrationPageLabels(model))
        return "backoffice/auth/register"
    }

    @GetMapping("/forgot-password")
    fun forgotPassword(model: Model): String {
        model.addAttribute(displayLanguageController.getLoginForgotPasswordLabels(model))
        return "backoffice/auth/forgotpassword"
    }
}