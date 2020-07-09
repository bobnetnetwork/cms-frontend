package network.bobnet.cms.util

import network.bobnet.cms.model.user.User
import network.bobnet.cms.repository.user.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

class LoggedInUser(private val userRepository: UserRepository){

    fun getUser(): User? {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)
                ?.request
        var loggedInUserCookieExists = false
        var username =""

        request?.cookies?.forEach {
            if(it.name == "logged_in_user"  && it.value.isNotEmpty()){
                loggedInUserCookieExists = true
                username = it.value
            }
        }
        return userRepository.findOneByUserName(username)
    }

    fun setUser(response: HttpServletResponse){
        val request= (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request


        var loggedInUserCookieExists = false

        request.cookies?.forEach {
            if(it.name == "logged_in_user" && it.value.isNotEmpty()){
                loggedInUserCookieExists = true
            }
        }

        if(!loggedInUserCookieExists){
            val principal: User = SecurityContextHolder.getContext().authentication.principal as User
            val cookie = Cookie("logged_in_user", principal.userName)
            cookie.maxAge = 7 * 24 * 60 * 60 // expires in 7 days
            response.addCookie(cookie)
        }
    }
}