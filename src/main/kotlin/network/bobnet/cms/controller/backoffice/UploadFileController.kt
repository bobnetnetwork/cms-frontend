package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.filestorage.FileStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@Controller
class UploadFileController {

    @Autowired
    lateinit var fileStorage: FileStorage

    @GetMapping("/upload")
    fun index(): String {
        return "multipartfile/uploadform.html"
    }

    @PostMapping("/upload")
    fun uploadMultipartFile(@RequestParam("uploadfile") file: MultipartFile, model: Model): String {
        return "multipartfile/uploadform.html"
    }
}