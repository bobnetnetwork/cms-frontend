package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.filestorage.FileStorage
import network.bobnet.cms.model.content.File
import java.util.stream.Collectors

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder


@Controller
class DownloadFileController {
    @Autowired
    lateinit var fileStorage: FileStorage

    /*
     * Retrieve Files' Information
     */
    @GetMapping("/files")
    fun getListFiles(model: Model): String {
        val fileInfos: List<File> = fileStorage.loadFiles().map{
            path -> File(path.fileName.toString(),
                MvcUriComponentsBuilder.fromMethodName(DownloadFileController::class.java,
                        "downloadFile", path.fileName.toString()).build().toString())
        }
                .collect(Collectors.toList())

        model.addAttribute("files", fileInfos)
        return "multipartfile/listfiles"
    }

    /*
     * Download Files
     */
    @GetMapping("/files/{filename}")
    fun downloadFile(@PathVariable filename: String): ResponseEntity<Resource> {
        val file = fileStorage.loadFile(filename)
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.filename + "\"")
                .body(file);
    }
}