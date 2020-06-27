package network.bobnet.cms.filestorage

import org.springframework.web.multipart.MultipartFile
import java.text.Normalizer


class ExtendedFile(var file: MultipartFile){
    var slug: String = ""
    var ext: String = ""
    var fullName = ""

    init{
        val name: String = file.originalFilename.toString().substring(0, file.originalFilename.toString().lastIndexOf('.'))
        slug = slugify(name)
        ext = file?.originalFilename?.lastIndexOf('.')?.let { file!!.originalFilename!!.substring(it+1, file!!.originalFilename!!.length ) }.toString()
        fullName = "$slug.$ext"
    }

    private fun slugify(word: String, replacement: String = "-") = Normalizer
            .normalize(word, Normalizer.Form.NFD)
            .replace("[^\\p{ASCII}]".toRegex(), "")
            .replace("[^a-zA-Z0-9\\s]+".toRegex(), "").trim()
            .replace("\\s+".toRegex(), replacement)
            .toLowerCase()
}