package network.bobnet.cms.controller.backoffice.administration

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.repository.administration.OptionsRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SettingsController(
        private val displayLanguageController: DisplayLanguageController,
        private val optionsRepository: OptionsRepository) {

    @GetMapping("/admin/settings")
    fun settings(model: Model): String {
        model.addAttribute(displayLanguageController.getSettingsLabels(model))
        return "/backoffice/administration/settings"
    }
}
