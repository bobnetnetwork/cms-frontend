package network.bobnet.cms.controller.frontend

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.model.content.Tag
import network.bobnet.cms.service.content.ArticleService
import org.apache.commons.text.StringEscapeUtils
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

@Controller
class FrontendArticleController(
        private val displayLanguageController: DisplayLanguageController,
        private val articleService: ArticleService,
        private val sidebar: SidebarController) {

    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        model.addAttribute(sidebar.addSidebar(model))
        val article = articleService
                .findBySlug(slug)
                .render()
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
        model["title"] = article.title
        article.content = StringEscapeUtils.unescapeHtml4(article.content)
        model["article"] = article
        model["author"] = article.author
        model["featuredImage"] = article.featuredImage
        val tagList = mutableListOf<Tag>()
        if (article.tags?.isNotEmpty()!!) {
            val tagIterator = article.tags.iterator()
            while (tagIterator.hasNext()) {
                tagList.add(tagIterator.next())
            }
        }

        model["tagList"] = tagList
        return "frontend/article"
    }
}
