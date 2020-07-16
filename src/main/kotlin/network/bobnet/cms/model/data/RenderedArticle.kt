package network.bobnet.cms.model.data

import network.bobnet.cms.model.content.Tag
import network.bobnet.cms.model.user.User
import java.time.LocalDateTime

data class RenderedArticle(
        val slug: String,
        val title: String,
        val headline: String,
        var content: String,
        val featuredImage: String,
        val author: User,
        val addedAt: LocalDateTime,
        val tags: MutableSet<Tag>?,
        val id: Long) {

}
