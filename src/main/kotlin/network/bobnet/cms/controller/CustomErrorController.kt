package network.bobnet.cms.controller

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest


@Controller
class CustomErrorController: ErrorController{

    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest): String? {
        val status: Int = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as Int
        if (status != null) {
           return "/error/$status"
        }
        return "/error/default"
    }

    @Override
    override fun getErrorPath(): String {
        return "/error"
    }

}