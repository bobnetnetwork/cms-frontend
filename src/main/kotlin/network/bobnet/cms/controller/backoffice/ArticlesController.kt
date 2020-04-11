package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.controller.DisplayLanguageController
import network.bobnet.cms.model.data.RenderedArticle
import network.bobnet.cms.model.user.User
import network.bobnet.cms.repository.content.ArticleRepository
import network.bobnet.cms.repository.content.CategoryRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Controller
class ArticlesController (
                    private val articleRepository: ArticleRepository,
                    private val displayLanguageController: DisplayLanguageController,
                    private val categoryRepository: CategoryRepository) {

    @GetMapping("/admin/articles")
    fun articles(model: Model): String{
        model.addAttribute(displayLanguageController.getArticlesLabels(model))
        model["articles"] = articleRepository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "backoffice/articles"
    }

    @GetMapping("/admin/articles/{slug}")
    fun article(@PathVariable slug: String, model: Model): String{
        model.addAttribute(displayLanguageController.getArticleEditorLabels(model))
        val article = if(slug == "new"){
            RenderedArticle("", "", "", "", "", User("", ""), LocalDateTime.now(), 0)
        }else{
            articleRepository
                    .findBySlug(slug)
                    ?.render()
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
        }
        model["articletitle"] = article.title
        model["articlecontent"] = article.content
        model["author"] = article.author
        model["featuredimage"] = article.featuredImage
        model["categories"] = categoryRepository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "backoffice/article"
    }

}
