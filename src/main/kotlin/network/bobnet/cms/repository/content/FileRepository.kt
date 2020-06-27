package network.bobnet.cms.repository.content

import network.bobnet.cms.model.content.File
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface FileRepository : PagingAndSortingRepository<File, Long>, JpaSpecificationExecutor<File> {
    fun findBySlug(slug: String): File
    fun findAllByOrderByAddedAtDesc(): Iterable<File>
    //fun findByCategoryIds(categoryIds: Long): Iterable<Article>
}