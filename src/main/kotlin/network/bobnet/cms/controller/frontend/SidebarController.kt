package network.bobnet.cms.controller.frontend

import network.bobnet.cms.service.content.ArticleService
import network.bobnet.cms.service.content.TagService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set

@Controller
class SidebarController(private val articleService: ArticleService, private val tagService: TagService) {

    fun addSidebar(model: Model): Model {
        model.addAttribute(addRecentPosts(model))
        model.addAttribute(addTagCloud(model))
        return model
    }

    private fun addRecentPosts(model: Model): Model {
        model["recentPosts"] = articleService.findAll(PageRequest.of(0, 5, Sort.by("addedAt").descending()))
        return model
    }

    private fun addTagCloud(model: Model): Model {
        model["tagCloud"] = tagService.findAll()
        return model
    }
}