package network.bobnet.cms.model.data

import java.time.LocalDateTime

data class RenderedFile(
        val fileName: String,
        val url: String,
        val slug: String,
        val mimeType: String,
        val addedAt: LocalDateTime,
        val id: Long)