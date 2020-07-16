package network.bobnet.cms.repository.administration

import network.bobnet.cms.model.Options
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OptionsRepository : CrudRepository<Options, Long> {
    fun findByName(name: String): Options?
}