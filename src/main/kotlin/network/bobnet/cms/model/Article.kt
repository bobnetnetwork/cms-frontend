package network.bobnet.cms.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Article(
        var title: String,
        var headline: String,
        var content: String,
        var featuredImage: String,
        @ManyToOne var author: User,
        @Column(name = "slug") var slug: String = title.toString(),
        var addedAt: LocalDateTime = LocalDateTime.now(),
        @Id @GeneratedValue var id: Long? = null)
