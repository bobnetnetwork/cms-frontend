package network.bobnet.cms.service.content

import network.bobnet.cms.model.content.Page
import network.bobnet.cms.repository.content.PageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = ["page"], cacheManager = "cacheManager", keyGenerator = "keyGenerator")
class PageService {
    @Autowired
    private lateinit var pageRepository: PageRepository

    private fun existsById(id: Long): Boolean {
        return pageRepository.existsById(id)
    }

    fun findById(id: Long): Page? {
        return pageRepository.findById(id).orElse(null)
    }

    @Cacheable
    fun findBySlug(slug: String): Page {
        return pageRepository.findBySlug(slug)
    }

    fun findAll(pageNumber: Int, rowNumber: Int): MutableList<Page> {
        val pages: MutableList<Page> = mutableListOf()
        val sortedByLastUpdateDesc: Pageable

        sortedByLastUpdateDesc = PageRequest.of(pageNumber - 1, rowNumber, Sort.by("id").ascending())
        pageRepository.findAll(sortedByLastUpdateDesc).forEach {
            pages.add(it)
        }

        return pages
    }

    fun findAll(pageabel: Pageable): org.springframework.data.domain.Page<Page> {
        return pageRepository.findAll(pageabel)
    }

    fun save(page: Page): Page {
        if (page.title.isEmpty()) {
            throw IllegalArgumentException("Title is required")
        }
        if (page.content.isEmpty()) {
            throw IllegalArgumentException("Content is required")
        }
        if (page.id != null && existsById(page.id!!)) {
            throw IllegalArgumentException("Page with id: " + page.id + " already exists")
        }
        return pageRepository.save(page)
    }

    fun update(page: Page) {
        if (page.title.isEmpty()) {
            throw IllegalArgumentException("Title is required")
        }
        if (page.content.isEmpty()) {
            throw IllegalArgumentException("Content is required")
        }
        if (!existsById(page.id!!)) {
            throw IllegalArgumentException("Cannot find Page with id: " + page.id)
        }
        pageRepository.save(page)
    }

    fun deleteById(id: Long) {
        if (!existsById(id)) {
            throw IllegalArgumentException("Cannot find Page with id: $id")
        } else {
            pageRepository.deleteById(id)
        }
    }

    fun count(): Long {
        return pageRepository.count()
    }

    fun findAllByOrderByAddedAtDesc(): Iterable<Page> {
        return pageRepository.findAllByOrderByAddedAtDesc()
    }
}