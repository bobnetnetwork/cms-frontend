package network.bobnet.cms.controller

import network.bobnet.cms.model.data.HttpErrorMessages
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest
import org.springframework.ui.Model
import org.springframework.ui.set


@Controller
class CustomErrorController(private val displayLanguageController: DisplayLanguageController): ErrorController{

    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest, model: Model): String? {
        val status: Int = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as Int
        val httpErrorMessage : HttpErrorMessages
        if (status != null) {
           httpErrorMessage = HttpErrorMessages(status)
        }else{
            httpErrorMessage = HttpErrorMessages(0)
        }
        displayLanguageController.getErrorPageLabels(model)
        model["code"] = status
        model["message"] = httpErrorMessage.message
        model["slug"] = httpErrorMessage.slug
        return "/error/error"
    }

    @Override
    override fun getErrorPath(): String {
        return "/error"
    }

}