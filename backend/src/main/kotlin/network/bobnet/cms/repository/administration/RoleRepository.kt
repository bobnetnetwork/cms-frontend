package network.bobnet.cms.repository.administration

import network.bobnet.cms.model.user.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByRolename(rolename: String): Role
}