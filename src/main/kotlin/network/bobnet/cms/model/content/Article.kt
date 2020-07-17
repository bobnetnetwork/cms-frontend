package network.bobnet.cms.model.content

import network.bobnet.cms.model.data.RenderedArticle
import network.bobnet.cms.model.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Article(
        var title: String = "",
        var headline: String = "",
        @Column(columnDefinition = "LONGTEXT")
        var content: String = "",
        var featuredImage: String = "",
        @ManyToOne(cascade = [CascadeType.ALL]) var author: User = User(),
        var slug: String = "",
        var addedAt: LocalDateTime = LocalDateTime.now(),
        @Id @GeneratedValue var id: Long? = null,
        @ManyToMany var tags: MutableSet<Tag>? = null,
        @ManyToMany var categories: MutableSet<Category>? = null) {

    fun render() = id?.let {
        RenderedArticle(
                slug,
                title,
                headline,
                content,
                featuredImage,
                author,
                addedAt,
                tags,
                it
        )
    }
}
