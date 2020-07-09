package network.bobnet.cms.service

import network.bobnet.cms.model.content.File
import network.bobnet.cms.repository.content.FileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class FileService{

    @Autowired
    private lateinit var fileRepository: FileRepository

    fun findById(id: Long): File? {
        return fileRepository.findById(id).orElse(null)
    }

    fun findBySlug(slug: String): File {
        return fileRepository.findBySlug(slug)
    }

    fun findAll(pageNumber: Int, rowNumber: Int): MutableList<File>{
        val files: MutableList<File> = mutableListOf()
        val sortedByLastUpdateDesc : Pageable

        sortedByLastUpdateDesc = PageRequest.of(pageNumber -1, rowNumber, Sort.by("id").ascending())
        fileRepository.findAll(sortedByLastUpdateDesc).forEach{
            files.add(it)
        }

        return files
    }

    fun save(file: File): File {
        return fileRepository.save(file)
    }

    fun count(): Long{
        return fileRepository.count()
    }
}
