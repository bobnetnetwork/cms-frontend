package network.bobnet.cms.model.data

import java.time.LocalDateTime

data class RenderedUser(
        val id: Long,
        val email: String,
        val firstName: String,
        val lastName: String,
        val userName: String,
        val registeredAt: LocalDateTime)