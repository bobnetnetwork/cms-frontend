package network.bobnet.cms.model.user

import network.bobnet.cms.model.data.RenderedUser
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class User(var firstName: String = "",
           var lastName: String = "",
           var userName: String = "",
           var email: String = "",
           var passWord: String = "") {
    @Id
    @GeneratedValue
    var id: Long = 0
    final var version: Int = 0
    final var accountNonExpired: Boolean = true
    final var accountNonLocked: Boolean = true
    final var credentialsNonExpired: Boolean = true
    final var enabled: Boolean = true
    final var registeredAt: LocalDateTime = LocalDateTime.now()
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var roles: MutableSet<Role> = HashSet()
    constructor(user: User) : this(user.firstName, user.lastName, user.userName, user.email, user.passWord) {
        id = user.id
        version = user.version
        firstName = user.firstName
        lastName = user.lastName
        userName = user.userName
        email = user.email
        passWord = user.passWord
        accountNonExpired = user.accountNonExpired
        accountNonLocked = user.accountNonLocked
        credentialsNonExpired = user.credentialsNonExpired
        enabled = user.enabled
        registeredAt = user.registeredAt
        roles = user.roles
    }

    fun render() {
        RenderedUser(
                id,
                email,
                firstName,
                lastName,
                userName,
                registeredAt

        )
    }
}
