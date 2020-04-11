package network.bobnet.cms.controller.api

import network.bobnet.cms.model.content.Category
import network.bobnet.cms.repository.content.CategoryRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(private val categoryRepository: CategoryRepository) {

    @GetMapping("/")
    fun findAll(): List<Category> =
            categoryRepository.findAll()

    @GetMapping("/{slug}")
    fun findOne(@PathVariable slug: String): ResponseEntity<Category>? {
        val category: Category? = categoryRepository.findBySlug(slug)
        val id: Long? = category?.id
        return id?.let {
            categoryRepository.findById(it).map { category ->
            ResponseEntity.ok(category)
        }.orElse(ResponseEntity.notFound().build())
        }
    }
}