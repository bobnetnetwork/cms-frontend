package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.controller.DisplayLanguageController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.security.Principal
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class BackOfficeHtmlController (private val displayLanguageController: DisplayLanguageController){

    @GetMapping("/admin")
    fun admin(request: HttpServletRequest, response: HttpServletResponse, principal: Principal, model: Model): String {
        val cookies = request.cookies
        var loggedInUserCookieExists: Boolean = false

        if (cookies != null){
            cookies.forEach {
                if(it.name.equals("logged_in_user")){
                    loggedInUserCookieExists = true;
                }
            }
        }

        if(!loggedInUserCookieExists){
            val cookie = Cookie("logged_in_user", principal.name)
            cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
            response.addCookie(cookie)
        }
        model.addAttribute(displayLanguageController.getDashboardLabels(model))
        return "backoffice/admin"
    }

    @GetMapping("/admin/profile")
    fun profile(model: Model): String{

        return "profile"
    }

    @GetMapping("/admin/users")
    fun users(model: Model): String{

        return "users"
    }

    @GetMapping("/admin/users/{username}")
    fun editUser(@PathVariable username: String, model: Model): String{

        return "edituser"
    }


}