package network.bobnet.cms.repository.administration

import network.bobnet.cms.model.user.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findOneByUserName(userName: String): User?
}