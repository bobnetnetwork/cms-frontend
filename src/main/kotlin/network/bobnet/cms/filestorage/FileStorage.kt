package network.bobnet.cms.filestorage

import java.nio.file.Path
import java.util.stream.Stream

import org.springframework.web.multipart.MultipartFile
import org.springframework.core.io.Resource

interface FileStorage{
    fun store(file: MultipartFile)
    fun loadFile(filename: String): Resource
    fun deleteAll()
    fun init()
    fun loadFiles(): Stream<Path>
}