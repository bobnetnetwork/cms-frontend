package network.bobnet.cms.repository.content

import network.bobnet.cms.model.content.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findBySlug(slug: String): Category?
    fun findAllByOrderByAddedAtDesc(): Iterable<Category>
}
