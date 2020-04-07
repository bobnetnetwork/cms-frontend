package network.bobnet.cms.model.content

import network.bobnet.cms.model.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Article(
        var title: String,
        var headline: String,
        var content: String,
        var featuredImage: String,
        @ManyToOne var author: User,
        var slug: String,
        var addedAt: LocalDateTime = LocalDateTime.now(),
        @Id @GeneratedValue var id: Long? = null,
        @ManyToMany val categoryIds: Set<Category>?= null)
