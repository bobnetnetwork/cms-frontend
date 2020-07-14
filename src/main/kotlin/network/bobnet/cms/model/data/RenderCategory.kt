package network.bobnet.cms.model.data

import network.bobnet.cms.model.content.Category

data class RenderCategory(
        val slug: String,
        val name: String,
        val featuredImage: String,
        val description: String,
        val parent: Category,
        val id: Long?)