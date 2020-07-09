package network.bobnet.cms.controller

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@ControllerAdvice
class ControllerAdvisor {
    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleFileSizeException(ex: MaxUploadSizeExceededException, redirectAttributes: RedirectAttributes): String {
        redirectAttributes.addFlashAttribute("error", "File is too big")
        return "redirect:/admin/file/upload/error/file_is_to_big"
    }
}