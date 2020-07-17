package network.bobnet.cms.model.data

import java.time.LocalDateTime

data class RenderedTag(
        val slug: String,
        val title: String,
        val addedAt: LocalDateTime,
        val id: Long)
