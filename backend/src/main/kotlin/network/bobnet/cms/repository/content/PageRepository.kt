package network.bobnet.cms.repository.content

import network.bobnet.cms.model.content.Page
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface PageRepository: PagingAndSortingRepository<Page, Long>, JpaSpecificationExecutor<Page> {
    fun findBySlug(slug: String): Page
    fun findAllByOrderByAddedAtDesc(): Iterable<Page>
}