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

    fun findAllCategories(): MutableList<Category>{
        return categoryRepository.findAll()
    }

    fun findAll(pageNumber: Int, rowNumber: Int): MutableList<Category> {
        val categories: MutableList<Category> = mutableListOf()
        val sortedByLastUpdateDesc: Pageable

        sortedByLastUpdateDesc = PageRequest.of(pageNumber - 1, rowNumber, Sort.by("id").ascending())
        categoryRepository.findAll(sortedByLastUpdateDesc).forEach {
            categories.add(it)
        }

        return categories
    }

    fun save(category: Category): Category {
        if (category.name.isEmpty()) {
            throw IllegalArgumentException("Name is required")
        }
        if (category.description.isEmpty()) {
            throw IllegalArgumentException("Description is required")
        }
        if (category.id != null && existsById(category.id!!)) {
            throw IllegalArgumentException("Category with id: " + category.id + " already exists")
        }
        return categoryRepository.save(category)
    }

    fun update(category: Category) {
        if (category.name.isEmpty()) {
            throw IllegalArgumentException("Name is required")
        }
        /*if (category.description.isEmpty()) {
            throw IllegalArgumentException("Description is required")
        }*/
        if (!existsById(category.id!!)) {
            throw IllegalArgumentException("Cannot find Category with id: " + category.id)
        }
        categoryRepository.save(category)
    }

    fun deleteById(id: Long) {
        if (!existsById(id)) {
            throw IllegalArgumentException("Cannot find Category with id: $id")
        } else {
            categoryRepository.deleteById(id)
        }
    }

    fun count(): Long {
        return categoryRepository.count()
    }

    fun countBySlug(slug: String): Long {
        return categoryRepository.countBySlug(slug)
    }
}