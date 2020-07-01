package network.bobnet.cms.controller

import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.rmi.UnexpectedException


@ControllerAdvice
class ControllerAdvisor {
    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleFileSizeException(ex: MaxUploadSizeExceededException, redirectAttributes: RedirectAttributes): String {
        redirectAttributes.addFlashAttribute("error", "File is too big")
        return "redirect:/admin/file/upload/error/file_is_to_big"
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handle(ex: NoHandlerFoundException): String? {
        val url = ex.requestURL
        val errorPage = url.substring(url.indexOf("/"), url.indexOf("/")+5)
        return ""
    }
}