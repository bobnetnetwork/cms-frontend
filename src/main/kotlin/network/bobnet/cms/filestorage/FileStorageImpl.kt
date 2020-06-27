package network.bobnet.cms.filestorage

import org.slf4j.Logger
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream

import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import java.lang.RuntimeException

@Service
class FileStorageImpl: FileStorage{

    val log: Logger = LoggerFactory.getLogger(this::class.java)
    var rootLocation: Path = Paths.get("filestorage")

    override fun store(file: MultipartFile): String{
        val extendedFile = ExtendedFile(file)

        Files.copy(file.inputStream, this.rootLocation.resolve(extendedFile.fullName))
        return "/filestorage/"+extendedFile.fullName
    }

    override fun loadFile(filename: String): Resource {
        val file = rootLocation.resolve(filename)
        val resource = UrlResource(file.toUri())

        if(resource.exists() || resource.isReadable){
            return resource
        }else{
            log.error("No souche file, or no readable!")
            throw RuntimeException("FAIL!")
        }
    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }

    override fun init() {
        Files.createDirectory(rootLocation)
    }

    override fun loadFiles(): Stream<Path> {
        return Files.walk(this.rootLocation, 1).filter{path -> path != this.rootLocation }.map(this.rootLocation::relativize)
    }
}