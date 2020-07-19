package network.bobnet.cms.controller.api

import network.bobnet.cms.model.Options
import network.bobnet.cms.repository.administration.OptionsRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/options")
class OptionsController(private val repository: OptionsRepository) {

    @GetMapping("/")
    fun findAll(): MutableIterable<Options> = repository.findAll()

    @GetMapping("/{name}")
    fun findOne(@PathVariable name: String) =
            repository.findByName(name) ?: ResponseStatusException(HttpStatus.NOT_FOUND, "This options does not exist")
}