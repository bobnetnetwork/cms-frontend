package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.repository.user.UserRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UsersController (
                private val userRepository: UserRepository,
                private val displayLanguageController: DisplayLanguageController){

    @GetMapping("/admin/users")
    fun users(model: Model): String{
        model.addAttribute(displayLanguageController.getUsersLabels(model))
        model["users"] = userRepository.findAll().map { it.render() }
        return "backoffice/users"
    }




}