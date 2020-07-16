package network.bobnet.cms.service.content

import network.bobnet.cms.model.content.Category
import network.bobnet.cms.repository.content.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CategoryService {

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    private fun existsById(id: Long): Boolean {
        return categoryRepository.existsById(id)
    }

    fun findById(id: Long): Category? {
        return categoryRepository.findById(id).orElse(null)
    }

    fun findBySlug(slug: String): Category? {
        return categoryRepository.findBySlug(slug)
    }

    fun findAll(pageNumber: Int, rowNumber: Int): MutableList<Category>{
        val articles: MutableList<Category> = mutableListOf()
        val sortedByLastUpdateDesc : Pageable

        sortedByLastUpdateDesc = PageRequest.of(pageNumber -1, rowNumber, Sort.by("id").ascending())
        categoryRepository.findAll(sortedByLastUpdateDesc).forEach{
            articles.add(it)
        }

        return articles
    }

    fun save(category: Category): Category {
        if(category.name.isEmpty()){
            throw Exception("Name is required")
        }
        if(category.description.isEmpty()){
            throw Exception("Description is required")
        }
        if(category.id != null && existsById(category.id!!)){
            throw Exception("Category with id: " + category.id + " already exists")
        }
        return categoryRepository.save(category)
    }

    fun update(category: Category){
        if(category.name.isEmpty()){
            throw Exception("Name is required")
        }
        if(category.description.isEmpty()){
            throw Exception("Description is required")
        }
        if(!existsById(category.id!!)){
            throw Exception("Cannot find Category with id: " + category.id)
        }
        categoryRepository.save(category)
    }

    fun deleteById(id: Long){
        if(!existsById(id)){
            throw Exception("Cannot find Category with id: $id")
        }else {
            categoryRepository.deleteById(id)
        }
    }

    fun count(): Long{
        return categoryRepository.count()
    }
}