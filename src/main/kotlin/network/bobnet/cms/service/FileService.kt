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

    private fun existsById(id: Long): Boolean {
        return fileRepository.existsById(id)
    }

    fun findById(id: Long): File? {
        return fileRepository.findById(id).orElse(null)
    }

    fun findBySlug(slug: String): File {
        return fileRepository.findBySlug(slug)
    }

    fun findAll(pageNumber: Int, rowNumber: Int): MutableList<File>{
        val articles: MutableList<File> = mutableListOf<File>()
        val sortedByLastUpdateDesc : Pageable

        sortedByLastUpdateDesc = PageRequest.of(pageNumber -1, rowNumber, Sort.by("id").ascending())
        fileRepository.findAll(sortedByLastUpdateDesc).forEach{
            articles.add(it)
        }

        return articles
    }

    fun save(file: File): File {
        /*if(article.title.isEmpty()){
            throw Exception("Title is required")
        }
        if(article.content.isEmpty()){
            throw Exception("Content is required")
        }
        if(article.id != null && existsById(article.id!!)){
            throw Exception("Article with id: " + article.id + " already exists")
        }*/
        return fileRepository.save(file)
    }

    fun update(file: File){
        /*if(article.title.isEmpty()){
            throw Exception("Title is required")
        }
        if(article.content.isEmpty()){
            throw Exception("Content is required")
        }
        if(!existsById(article.id!!)){
            throw Exception("Cannot find Article with id: " + article.id)
        }*/
        fileRepository.save(file)
    }

    fun deleteById(id: Long){
        if(!existsById(id)){
            throw Exception("Cannot find Article with id: $id")
        }else {
            fileRepository.deleteById(id)
        }
    }

    fun count(): Long{
        return fileRepository.count()
    }
}