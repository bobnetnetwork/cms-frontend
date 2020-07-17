package network.bobnet.cms.service.content

import network.bobnet.cms.model.content.Article
import network.bobnet.cms.model.content.Tag
import network.bobnet.cms.repository.content.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class TagService {

    @Autowired
    private lateinit var tagRepository: TagRepository

    private fun existsById(id: Long): Boolean {
        return tagRepository.existsById(id)
    }

    fun findById(id: Long): Tag? {
        return tagRepository.findById(id).orElse(null)
    }

    fun findBySlug(slug: String): Tag {
        return tagRepository.findBySlug(slug)
    }

    fun findAll(pageNumber: Int, rowNumber: Int): MutableList<Tag> {
        val articles: MutableList<Tag> = mutableListOf()
        val sortedByLastUpdateDesc: Pageable

        sortedByLastUpdateDesc = PageRequest.of(pageNumber - 1, rowNumber, Sort.by("id").ascending())
        tagRepository.findAll(sortedByLastUpdateDesc).forEach {
            articles.add(it)
        }

        return articles
    }

    fun save(tag: Tag): Tag {
        if (tag.title.isEmpty()) {
            throw Exception("Title is required")
        }
        if (tag.id != null && existsById(tag.id!!)) {
            throw Exception("Tag with id: " + tag.id + " already exists")
        }
        return tagRepository.save(tag)
    }

    fun update(tag: Tag) {
        if (tag.title.isEmpty()) {
            throw Exception("Title is required")
        }
        if (!existsById(tag.id!!)) {
            throw Exception("Cannot find Tag with id: " + tag.id)
        }
        tagRepository.save(tag)
    }

    fun deleteById(id: Long) {
        if (!existsById(id)) {
            throw Exception("Cannot find Tag with id: $id")
        } else {
            tagRepository.deleteById(id)
        }
    }

    fun count(): Long {
        return tagRepository.count()
    }

    fun countByTitle(title: String): Long {
        return tagRepository.countByTitle(title)
    }

    fun findByTitle(title: String): Tag {
        return tagRepository.findByTitle(title)
    }

    fun findAll(): Iterable<Tag> {
        return tagRepository.findAll()
    }

    fun findArticlesByTag(tagSlug: String): Iterable<Article> {
        val tag = findBySlug(tagSlug)
        val articles = mutableListOf<Article>()
        if (tag.articles?.isNotEmpty()!!) {
            val articleIterator = tag.articles!!.iterator()
            while (articleIterator.hasNext()) {
                articles.add(articleIterator.next())
            }
        }
        return articles
    }

}
