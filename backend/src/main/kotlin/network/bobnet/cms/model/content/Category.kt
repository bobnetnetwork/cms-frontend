package network.bobnet.cms.model.content

import network.bobnet.cms.model.data.RenderCategory
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Category(
        var name: String = "",
        var description: String = "",
        var featuredImage: String = "",
        @Column(unique = true) var slug: String = "",
        var addedAt: LocalDateTime = LocalDateTime.now(),
        @Id @GeneratedValue var id: Long? = null,
        @ManyToOne var parent: Category? = null,
        @ManyToMany val articles: MutableSet<Article>? = null) {

    fun render() = RenderCategory(
            slug,
            name,
            featuredImage,
            description,
            parent,
            id
    )
}
