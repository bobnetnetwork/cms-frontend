package network.bobnet.cms.filestorage

import network.bobnet.cms.util.Extensions
import org.springframework.web.multipart.MultipartFile

class ExtendedFile(var file: MultipartFile){
    var slug: String = ""
    private var ext: String = ""
    var fullName: String = ""
    private val extensions = Extensions()

    init{
        val name: String = file.originalFilename.toString().substring(0, file.originalFilename.toString().lastIndexOf('.'))
        slug = extensions.slugify(name)
        ext = file.originalFilename?.lastIndexOf('.')?.let { file.originalFilename!!.substring(it+1, file.originalFilename!!.length ) }.toString()
        fullName = "$slug.$ext"
    }
}
