package network.bobnet.cms.controller.frontend

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.model.content.Tag
import network.bobnet.cms.repository.content.ArticleRepository
import network.bobnet.cms.repository.content.CategoryRepository
import network.bobnet.cms.repository.content.TagRepository
import org.apache.commons.text.StringEscapeUtils
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

@Controller
class FrontendHtmlController(private val articleRepository: ArticleRepository,
                             private val categoryRepository: CategoryRepository,
                             private val displayLanguageController: DisplayLanguageController,
                             private val tagRepository: TagRepository) {

    @GetMapping("/")
    fun blog(model: Model): String {
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        model.addAttribute(addSidebar(model))
        model["articles"] = articleRepository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "frontend/blog"
    }

    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        model.addAttribute(addSidebar(model))
        val article = articleRepository
                .findBySlug(slug)
                .render()
                ?: throw ResponseStatusException(NOT_FOUND, "This article does not exist")
        model["title"] = article.title
        article.content = StringEscapeUtils.unescapeHtml4(article.content)
        model["article"] = article
        model["featuredImage"] = article.featuredImage
        val tagList = mutableListOf<Tag>()
        if(article.tags?.isNotEmpty()!!){
            val tagIterator = article.tags.iterator()
            while (tagIterator.hasNext()){
                tagList.add(tagIterator.next())
            }
        }

        model["tagList"] = tagList
        return "frontend/article"
    }

    @GetMapping("/categories/{slug}")
    fun category(@PathVariable slug: String, model: Model): String {
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        model.addAttribute(addSidebar(model))
        val category = categoryRepository
                .findBySlug(slug)
                ?.render()
                ?: throw ResponseStatusException(NOT_FOUND, "This article does not exist")
        model["ctageoryname"] = category.name
        model["featuredimage"] = category.featuredImage
        model["categorydescription"] = category.description
        //model["articles"] = category.id?.let { it -> repository.findByCategoryIds(it).map { it.render() } }!!
        return "frontend/category"
    }

    @GetMapping("/categories")
    fun categories(model: Model): String{
        model.addAttribute(displayLanguageController.getFrontendLabels(model))
        model.addAttribute(addSidebar(model))
        model["categories"] = categoryRepository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "frontend/categories"
    }

    private fun addSidebar(model: Model): Model{
        model.addAttribute(addRecentPosts(model))
        model.addAttribute(addTagCloud(model))
        return model
    }

    private fun addRecentPosts(model: Model): Model{
        model["recentPosts"] = articleRepository.findAll(PageRequest.of(0, 5, Sort.by("addedAt").descending()))
        return model
    }

    private fun addTagCloud(model: Model): Model{
        model["tagCloud"] = tagRepository.findAll()
        return model
    }

}
