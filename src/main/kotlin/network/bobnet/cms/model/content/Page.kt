package network.bobnet.cms.model.content

import network.bobnet.cms.model.data.RenderedPage
import network.bobnet.cms.model.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Page(var title: String = "",
                var headline: String = "",
                @Column(columnDefinition = "LONGTEXT")
                var content: String = "",
                var featuredImage: String = "",
                @ManyToOne(cascade = [CascadeType.ALL]) var author: User = User(),
                var slug: String = "",
                var addedAt: LocalDateTime = LocalDateTime.now(),
                @Id @GeneratedValue var id: Long? = null,){

    fun render() = id?.let {
        RenderedPage(
                slug,
                title,
                headline,
                content,
                featuredImage,
                author,
                addedAt,
                it
        )
    }
}