package network.bobnet.cms.security

import network.bobnet.cms.model.user.User
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class CustomUserDetails(user: User) : User(user), UserDetails {

    private val log = LoggerFactory.getLogger(CustomUserDetails::class.java)

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles
                .stream()
                .map { role ->
                    log.debug("Granting Authority to user with role: $role")
                    SimpleGrantedAuthority(role.toString())
                }
                .collect(Collectors.toList())
    }

    override fun getPassword(): String {
        return super.passWord
    }

    override fun getUsername(): String {
        return super.userName
    }

    override fun isEnabled(): Boolean {
        return super.enabled
    }

    override fun isCredentialsNonExpired(): Boolean {
        return super.credentialsNonExpired
    }

    override fun isAccountNonExpired(): Boolean {
        return super.accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return super.accountNonLocked
    }
}