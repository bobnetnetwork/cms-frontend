package network.bobnet.cms.model.content

import network.bobnet.cms.model.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Page(var title: String,
                var headline: String,
                var content: String,
                @ManyToOne var author: User,
                @Column(name = "slug") var slug: String = title.toString(),
                var addedAt: LocalDateTime = LocalDateTime.now(),
                @Id @GeneratedValue var id: Long? = null)