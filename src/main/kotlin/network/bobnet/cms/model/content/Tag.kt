package network.bobnet.cms.model.content

import network.bobnet.cms.model.data.RenderedTag
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
class Tag(
        var title: String = "",
        var slug: String = "",
        var addedAt: LocalDateTime = LocalDateTime.now(),
        @Id @GeneratedValue var id: Long? = null,
        @ManyToMany var articles: MutableSet<Article>? = null) {

    fun render() = id?.let {
        RenderedTag(
                slug,
                title,
                addedAt,
                it
        )
    }

}
