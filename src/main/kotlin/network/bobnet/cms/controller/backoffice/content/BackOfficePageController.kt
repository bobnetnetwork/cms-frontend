package network.bobnet.cms.controller.backoffice.content

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.model.content.Page
import network.bobnet.cms.repository.administration.UserRepository
import network.bobnet.cms.service.content.PageService
import network.bobnet.cms.util.Extensions
import network.bobnet.cms.util.LoggedInUser
import org.apache.commons.text.StringEscapeUtils
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class BackOfficePageController(
        private val displayLanguageController: DisplayLanguageController,
        private val pageService: PageService,
        private val userRepository: UserRepository) {

    private final val ROW_PER_PAGE: Int = 5

    private final val PAGES_TEMPLATE = "backoffice/content/pages"
    private final val PAGE_TEMPLATE = "backoffice/content/page"

    private val extensions = Extensions()

    @GetMapping("/admin/pages")
    fun articles(model: Model, @RequestParam(value = "page", defaultValue = "1") pageNumber: Int): String {
        val pages = pageService.findAll(pageNumber, ROW_PER_PAGE)
        val count = pageService.count()
        val hasPrev: Boolean = pageNumber > 1
        val hasNext: Boolean = pageNumber * ROW_PER_PAGE < count

        model.addAttribute(displayLanguageController.getPagesLabels(model))
        model["pages"] = pages
        model["hasPrev"] = hasPrev
        model["prev"] = pageNumber - 1
        model["hasNext"] = hasNext
        model["next"] = pageNumber + 1

        return PAGES_TEMPLATE
    }

    @GetMapping("/admin/pages/{slug}")
    fun showEditArticle(@PathVariable slug: String, model: Model): String {
        model.addAttribute(displayLanguageController.getPageEditorLabels(model))

        val page = pageService.findBySlug(slug)
        page.content = StringEscapeUtils.unescapeHtml4(page.content)
        model["add"] = false
        model["article"] = page

        return PAGE_TEMPLATE

    }

    @PostMapping("/admin/pages/{slug}")
    fun editArticle(@PathVariable slug: String, model: Model, @RequestParam queryMap: Map<String, String>): String {
        model.addAttribute(displayLanguageController.getPageEditorLabels(model))

        val page = pageService.findBySlug(slug)
        page.content = StringEscapeUtils.escapeHtml4(queryMap["content"].toString())
        page.title = queryMap["title"].toString()

        pageService.update(page)
        return "redirect:/admin/pages/" + page.slug

    }

    @GetMapping("/admin/pages/new")
    fun showAddArticle(model: Model): String {
        model.addAttribute(displayLanguageController.getPageEditorLabels(model))
        val page = Page()
        model["add"] = true
        model["page"] = page

        return PAGE_TEMPLATE
    }

    @PostMapping("/admin/pages/new")
    fun addArticle(model: Model, @RequestParam queryMap: Map<String, String>): String {
        model.addAttribute(displayLanguageController.getPageEditorLabels(model))

        val page = Page()
        page.author = LoggedInUser(userRepository).getUser()!!
        page.title = queryMap["title"].toString()
        page.slug = extensions.slugify(queryMap["title"].toString())
        page.content = StringEscapeUtils.escapeHtml4(queryMap["content"].toString())
        val newPage: Page = pageService.save(page)
        return "redirect:/admin/pages/" + newPage.slug

    }
}