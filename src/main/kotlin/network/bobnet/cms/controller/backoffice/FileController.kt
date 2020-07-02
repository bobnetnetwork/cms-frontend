package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.filestorage.ExtendedFile
import network.bobnet.cms.filestorage.FileStorage
import network.bobnet.cms.model.content.File
import network.bobnet.cms.service.FileService
import network.bobnet.cms.service.LogService
import network.bobnet.cms.util.Translator
import org.apache.commons.io.IOUtils
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.multipart.MultipartFile

import org.springframework.web.bind.annotation.*
import java.io.InputStream
import java.nio.file.Files
import javax.servlet.http.HttpServletResponse

@Controller
class FileController(
        private val displayLanguageController: DisplayLanguageController,
        private val fileService: FileService) {

    private final val ROW_PER_PAGE = 10
    private val logger: LogService = LogService(this.javaClass)
    @Value("\${spring.http.multipart.max-file-size}")
    private lateinit var uploadMaxSize: String


    @Autowired
    lateinit var fileStorage: FileStorage

    @GetMapping("/admin/file")
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
        return "backoffice/file/list"
    }

    @GetMapping("/admin/file/upload")
    fun fileUpload(model: Model): String{
        model.addAttribute(displayLanguageController.getMediaLabels(model))
        return "backoffice/file/upload"
    }

    @PostMapping("/admin/file/upload")
    fun uploadMultipartFile(@RequestParam("uploadfile") file: MultipartFile, model: Model): String {
        try{
            model.addAttribute(displayLanguageController.getMediaLabels(model))
            val newFile = File()
            val extendedFile = ExtendedFile(file)
            val originallyName: String = file.originalFilename.toString()
            newFile.fileName = originallyName.substring(0, originallyName.lastIndexOf('.'))
            newFile.mimeType = file.contentType.toString()
            newFile.slug = extendedFile.slug
            newFile.url = fileStorage.store(file)

            fileService.save(newFile)
        }catch(ex: FileSizeLimitExceededException){
            model["error"] = true
            model["errorMessage"] = Translator.toLocale("lang.fileSizeLimitExceededException") + " " + uploadMaxSize + "!"
            logger.error(ex.stackTrace.toString())
            return "redirect:/admin/file/upload"
        }
        catch (ex: Exception){
            logger.error(ex.stackTrace.toString())
        }

        return "redirect:/admin/file/"
    }

    @PostMapping("/admin/file/upload/error/{message}")
    fun uploadMultipartFileFromError(@RequestParam("uploadfile") file: MultipartFile, model: Model): String {
        return uploadMultipartFile(file, model)
    }

    @GetMapping("/admin/file/{slug}")
    fun showMedia(model: Model, @PathVariable slug: String): String{
        model.addAttribute(displayLanguageController.getMediaLabels(model))
        val newFile: File = fileService.findById(slug.toLong())!!
        val ext: String = newFile.mimeType.substring(newFile.mimeType.lastIndexOf('/')+1, newFile.mimeType.lastIndex+1)
        var url: String =  model.getAttribute("home") as String
        url += "/filestorage/" + newFile.fileName + "." + ext
        model["mediaURL"] = url
        return "backoffice/file/show"
    }

    @GetMapping("/filestorage/{slug}")
    @ResponseBody
    fun downloadFile(@PathVariable slug: String, response: HttpServletResponse){
        val file = fileStorage.loadFile(slug)
        val ins: InputStream = file.inputStream
        response.contentType = Files.probeContentType(file.file.toPath())
        IOUtils.copy(ins, response.outputStream)
    }

    @GetMapping("/admin/file/upload/error/{message}")
    fun uploadError(model: Model, @PathVariable message: String): String{
        model.addAttribute(displayLanguageController.getMediaLabels(model))
        model["error"] = true
        when(message){
            "file_is_to_big" -> model["errorMessage"] = Translator.toLocale("lang.fileSizeLimitExceededException") + " " + uploadMaxSize + "!"
        }
        return "backoffice/file/upload"
    }
}