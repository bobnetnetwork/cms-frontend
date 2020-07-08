package network.bobnet.cms.controller

import network.bobnet.cms.repository.OptionsRepository
import network.bobnet.cms.repository.user.RoleRepository
import network.bobnet.cms.repository.user.UserRepository
import network.bobnet.cms.service.SetupService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletRequest

@Controller
class SetupController(private val optionsRepository: OptionsRepository, private val userRepository: UserRepository, private val roleRepository: RoleRepository) {

    @GetMapping("/setup")
    fun showSetupPage(model: Model): String{
        return "/setup"
    }

    @PostMapping("/setup")
    fun runSetup(request: HttpServletRequest, model: Model, @RequestParam queryMap: Map<String, String>):String {
        val installer = SetupService(optionsRepository, userRepository, roleRepository)

        val requestURL = request.requestURL.toString()

        installer.siteName = queryMap["siteName"].toString()
        installer.siteDescription = queryMap["siteDescription"].toString()
        installer.home = requestURL.substring(0, requestURL.lastIndexOf("/"))
        installer.language = queryMap["language"].toString()
        installer.copyright = queryMap["copyright"].toString()
        installer.userName = queryMap["userName"].toString()
        installer.password = queryMap["password"].toString()

        installer.save()

        return ""
    }
}
