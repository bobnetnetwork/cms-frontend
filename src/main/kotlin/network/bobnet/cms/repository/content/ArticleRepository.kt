package network.bobnet.cms.repository.content

import network.bobnet.cms.model.content.Article
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
@Repository
interface ArticleRepository : PagingAndSortingRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    fun findBySlug(slug: String): Article
    fun findAllByOrderByAddedAtDesc(): Iterable<Article>
    //fun findByCategoryIds(categoryIds: Long): Iterable<Article>
}