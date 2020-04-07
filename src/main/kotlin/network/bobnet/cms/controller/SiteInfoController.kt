package network.bobnet.cms.controller

import network.bobnet.cms.model.Options
import network.bobnet.cms.repository.OptionsRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.server.ResponseStatusException

@Controller
class SiteInfoController(private val optionsRepository: OptionsRepository) {

    fun getSiteInfo(model: Model): Model {
        val names = mutableListOf<String>()
        names.add("sitename")
        names.add("sitedescription")
        names.add("home")
        for (name in names) {
            val options = optionsRepository
                    .findByName(name)
                    ?.render()
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This options does not exist")
            model[name] = options.value
        }
        return model
    }

    fun Options.render() = RenderOptions(
            name,
            value)

    data class RenderOptions(
            val name: String,
            val value: String)
}