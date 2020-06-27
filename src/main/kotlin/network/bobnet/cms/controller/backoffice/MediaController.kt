package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.filestorage.FileStorage
import network.bobnet.cms.model.content.File
import network.bobnet.cms.repository.content.FileRepository
import network.bobnet.cms.service.FileService
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.multipart.MultipartFile

import org.springframework.web.bind.annotation.*
import java.io.InputStream
import java.nio.file.Files
import javax.servlet.http.HttpServletResponse

@Controller
class MediaController(
        private val fileRepository: FileRepository,
        private val displayLanguageController: DisplayLanguageController,
        private val fileService: FileService) {

    private final val ROW_PER_PAGE = 10

    @Autowired
    lateinit var fileStorage: FileStorage

    @GetMapping("/admin/media")
    fun files(model: Model, @RequestParam(value = "page", defaultValue = "1") pageNumber: Int): String{
        val files = fileService.findAll(pageNumber, ROW_PER_PAGE)
        val count = fileService.count()
        val hasPrev: Boolean = pageNumber > 1
        val hasNext: Boolean = pageNumber * ROW_PER_PAGE < count

        model.addAttribute(displayLanguageController.getMediaLabels(model))
        model["files"] = files
        model["hasPrev"] = hasPrev
        model["prev"] = pageNumber - 1
        model["hasNext"] = hasNext
        model["next"] = pageNumber + 1
        return "backoffice/media"
    }

    @GetMapping("/admin/media/upload")
    fun fileUpload(model: Model): String{
        model.addAttribute(displayLanguageController.getMediaLabels(model))
        return "backoffice/media/upload"
    }

    @PostMapping("/admin/media/upload")
    fun uploadMultipartFile(@RequestParam("uploadfile") file: MultipartFile, model: Model): String {
        model.addAttribute(displayLanguageController.getMediaLabels(model))
        var media: File = File()
        var originalfileName: String = file.originalFilename.toString()
        media.fileName = originalfileName.substring(0, originalfileName.lastIndexOf('.'))
        media.mimeType = file.contentType.toString()
        //fileStorage.init()
        media.url = fileStorage.store(file)

        fileService.save(media)
        return "redirect:/admin/media/"
    }

    @GetMapping("/admin/media/{slug}")
    fun showMedia(@PathVariable slug: String): String{

        return "backoffice/media/show"
    }

    @GetMapping("/filestorage/{slug}")
    @ResponseBody
    fun downloadFile(@PathVariable slug: String, response: HttpServletResponse){
        val file = fileStorage.loadFile(slug)
        val ins: InputStream = file.inputStream
        response.contentType = Files.probeContentType(file.file.toPath())
        IOUtils.copy(ins, response.outputStream)
    }
}