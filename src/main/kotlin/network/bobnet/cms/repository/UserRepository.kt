package network.bobnet.cms.repository

import network.bobnet.cms.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findByLogin(login: String): User?
}