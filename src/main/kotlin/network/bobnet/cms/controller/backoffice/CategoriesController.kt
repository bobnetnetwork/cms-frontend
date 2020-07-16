package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.model.content.Category
import network.bobnet.cms.service.CategoryService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*

@Controller
class CategoriesController (
                        private val displayLanguageController: DisplayLanguageController,
                        private val categoryService: CategoryService){

    private final val ROW_PER_PAGE: Int = 5

    private final val CATEGORIES_TEMPLATE = "backoffice/categories"
    private final val CATEGORY_TEMPLATE = "backoffice/category"

    @GetMapping("/admin/categories")
    fun categories(model: Model, @RequestParam(value = "page", defaultValue = "1") pageNumber: Int): String{
        val categories = categoryService.findAll(pageNumber, ROW_PER_PAGE)
        val count = categoryService.count()
        val hasPrev: Boolean = pageNumber > 1
        val hasNext: Boolean = pageNumber * ROW_PER_PAGE < count

        model.addAttribute(displayLanguageController.getCategoriesLabels(model))
        model["categories"] = categories
        model["hasPrev"] = hasPrev
        model["prev"] = pageNumber - 1
        model["hasNext"] = hasNext
        model["next"] = pageNumber + 1
        return CATEGORIES_TEMPLATE
    }

    @GetMapping("/admin/categories/{slug}")
    fun showCategory(@PathVariable slug: String, model: Model): String{

        return CATEGORY_TEMPLATE
    }

    @PostMapping("/admin/categories/{slug}")
    fun editCategory(@PathVariable slug: String, model: Model, @ModelAttribute("category") category: Category): String{

        return CATEGORY_TEMPLATE
    }

    @GetMapping("/admin/categories/new")
    fun showNewCategory(model: Model): String{

        return CATEGORY_TEMPLATE
    }

    @PostMapping("/admin/categories/new")
    fun addNewCategory(model: Model, @ModelAttribute("category") category: Category): String{

        return CATEGORY_TEMPLATE
    }
}
