package network.bobnet.cms.model.content

import network.bobnet.cms.model.data.RenderedTag
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Tag (
        var title: String = "",
        var slug: String = "",
        var addedAt: LocalDateTime = LocalDateTime.now(),
        @Id @GeneratedValue var id: Long? = null,
        @ManyToMany val articles: Set<Article>?= null){

    fun render() = id?.let {
        RenderedTag(
                slug,
                title,
                addedAt,
                it
        )
    }

}
