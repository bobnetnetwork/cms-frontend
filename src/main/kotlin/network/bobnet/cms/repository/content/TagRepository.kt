package network.bobnet.cms.repository.content

import network.bobnet.cms.model.content.Tag
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : PagingAndSortingRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {
    fun findBySlug(slug: String): Tag
    fun findAllByOrderByAddedAtDesc(): Iterable<Tag>
    fun findByTitle(title: String): Tag
    fun countByTitle(title: String): Long
}
