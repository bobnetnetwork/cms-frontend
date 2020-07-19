package network.bobnet.cms.controller.backoffice.content

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.model.content.Tag
import network.bobnet.cms.service.content.TagService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*

@Controller
class BackOfficeTagController(private val displayLanguageController: DisplayLanguageController,
                              private val tagService: TagService) {

    private final val ROW_PER_PAGE: Int = 5

    private final val TAGS_TEMPLATE = "backoffice/content/tags"
    private final val TAG_TEMPLATE = "backoffice/content/tag"

    @GetMapping("/admin/tags")
    fun tags(model: Model, @RequestParam(value = "page", defaultValue = "1") pageNumber: Int): String {
        model.addAttribute(displayLanguageController.getTagsLabels(model))

        val tags = tagService.findAll(pageNumber, ROW_PER_PAGE)
        val count = tagService.count()
        val hasPrev: Boolean = pageNumber > 1
        val hasNext: Boolean = pageNumber * ROW_PER_PAGE < count
        model["tags"] = tags
        model["hasPrev"] = hasPrev
        model["prev"] = pageNumber - 1
        model["hasNext"] = hasNext
        model["next"] = pageNumber + 1

        return TAGS_TEMPLATE
    }

    @GetMapping("/admin/tags/{slug}")
    fun showCategory(@PathVariable slug: String, model: Model): String {

        return TAG_TEMPLATE
    }

    @PostMapping("/admin/tags/{slug}")
    fun editCategory(@PathVariable slug: String, model: Model, @ModelAttribute("tag") tag: Tag): String {

        return TAG_TEMPLATE
    }

    @GetMapping("/admin/tags/new")
    fun showNewCategory(model: Model): String {

        return TAG_TEMPLATE
    }

    @PostMapping("/admin/tags/new")
    fun addNewCategory(model: Model, @ModelAttribute("tag") tag: Tag): String {

        return TAG_TEMPLATE
    }
}
