package network.bobnet.cms.controller

import network.bobnet.cms.repository.administration.OptionsRepository
import network.bobnet.cms.repository.administration.RoleRepository
import network.bobnet.cms.repository.administration.UserRepository
import network.bobnet.cms.service.SetupService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletRequest

@Controller
class SetupController(private val displayLanguageController: DisplayLanguageController, private val optionsRepository: OptionsRepository, private val userRepository: UserRepository, private val roleRepository: RoleRepository) {

    @GetMapping("/setup")
    fun showSetupPage(model: Model): String{
        model.addAttribute(displayLanguageController.getDashboardLabels(model))
        return "/setup"
    }

    @PostMapping("/setup")
    fun runSetup(request: HttpServletRequest, model: Model, @RequestParam queryMap: Map<String, String>):String {
        model.addAttribute(displayLanguageController.getDashboardLabels(model))
        val installer = SetupService(optionsRepository, userRepository, roleRepository)

        val requestURL = request.requestURL.toString()

        installer.siteName = queryMap["siteName"].toString()
        installer.siteDescription = queryMap["siteDescription"].toString()
        installer.home = requestURL.substring(0, requestURL.lastIndexOf("/"))
        installer.language = "en"
        installer.copyright = queryMap["copyright"].toString()
        installer.userName = queryMap["userName"].toString()
        installer.password = queryMap["password"].toString()
        installer.userMail = queryMap["userMail"].toString()

        installer.save()

        return "/setup_done"
    }
}
