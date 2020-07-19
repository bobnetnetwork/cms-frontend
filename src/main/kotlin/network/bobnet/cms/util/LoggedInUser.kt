package network.bobnet.cms.util

import network.bobnet.cms.model.user.User
import network.bobnet.cms.repository.administration.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

class LoggedInUser(private val userRepository: UserRepository) {

    private val LOGGED_IN_USER = "logged_in_user"

    fun getUser(): User? {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)
                ?.request
        var loggedInUserCookieExists = false
        var username = ""

        request?.cookies?.forEach {
            if (it.name == LOGGED_IN_USER && it.value.isNotEmpty()) {
                loggedInUserCookieExists = true
                username = it.value
            }
        }
        return userRepository.findOneByUserName(username)
    }

    fun setUser(response: HttpServletResponse) {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request


        var loggedInUserCookieExists = false

        request.cookies?.forEach {
            if (it.name == LOGGED_IN_USER && it.value.isNotEmpty()) {
                loggedInUserCookieExists = true
            }
        }

        if (!loggedInUserCookieExists) {
            val principal: User = SecurityContextHolder.getContext().authentication.principal as User
            val cookie = Cookie(LOGGED_IN_USER, principal.userName)
            cookie.maxAge = 7 * 24 * 60 * 60 // expires in 7 days
            response.addCookie(cookie)
        }
    }
}
