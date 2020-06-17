package network.bobnet.cms.model.data

import network.bobnet.cms.model.user.User
import java.time.LocalDateTime

data class RenderedArticle(
        val slug: String,
        val title: String,
        val headline: String,
        val content: String,
        val featuredImage: String,
        val author: User,
        val addedAt: LocalDateTime,
        val id: Long)