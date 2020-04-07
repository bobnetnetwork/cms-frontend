package network.bobnet.cms.controller.backend


import network.bobnet.cms.controller.SiteInfoController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthController(private val siteInfoController: SiteInfoController) {
    @GetMapping("/login")
    fun login(model: Model): String {
        model.addAttribute(siteInfoController.getSiteInfo(model))
        return "login"
    }

    @GetMapping("/register")
    fun register(model: Model): String {
        model.addAttribute(siteInfoController.getSiteInfo(model))
        return "register"
    }

    @GetMapping("/forgot-password")
    fun forgotPassword(model: Model): String {
        model.addAttribute(siteInfoController.getSiteInfo(model))
        return "forgotpassword"
    }
}