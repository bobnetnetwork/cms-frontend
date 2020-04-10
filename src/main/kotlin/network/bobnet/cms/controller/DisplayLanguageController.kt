package network.bobnet.cms.controller

import network.bobnet.cms.model.Options
import network.bobnet.cms.model.user.User
import network.bobnet.cms.repository.OptionsRepository
import network.bobnet.cms.repository.user.UserRepository
import network.bobnet.cms.util.LoggedInUser
import network.bobnet.cms.util.Translator
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.server.ResponseStatusException
import java.util.*


@Controller
class DisplayLanguageController(private val optionsRepository: OptionsRepository,
                                private val userRepository: UserRepository) {

    private fun getSiteInfo(model: Model): Model {
        val names = mutableListOf<String>()
        names.add("sitename")
        names.add("sitedescription")
        names.add("home")
        names.add("sitelanguage")
        names.add("tinymcelang")
        names.add("copyright")
        for (name in names) {
            val options = optionsRepository
                    .findByName(name)
                    ?.render()
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This options does not exist")
            model[name] = options.value
        }
        LocaleContextHolder.setLocale(Locale(model.getAttribute("sitelanguage") as String?))
        return model
    }

    private fun getSideBarLabels(model: Model): Model {
        model["lang.dashboard"] = Translator.toLocale("lang.dashboard")
        model["lang.contents"] = Translator.toLocale("lang.contents")
        model["lang.articles"] = Translator.toLocale("lang.articles")
        model["lang.categories"] = Translator.toLocale("lang.categories")
        model["lang.tags"] = Translator.toLocale("lang.tags")
        model["lang.pages"] = Translator.toLocale("lang.pages")
        model["lang.aministration"] = Translator.toLocale("lang.aministration")
        model["lang.users"] = Translator.toLocale("lang.users")
        model["lang.roles"] = Translator.toLocale("lang.roles")
        model["lang.settings"] = Translator.toLocale("lang.settings")
        return model
    }

    private fun getTopBarLabels(model: Model): Model {
        val user = LoggedInUser(userRepository).getUser()
        if (user != null) {
            model["loggedInUserFirstName"] = user.firstName
            model["loggedInUserLastName"] = user.lastName
        }
        return model
    }

    private fun getBasicsLabelsAndInfos(model: Model): Model {
        getSiteInfo(model)
        getSideBarLabels(model)
        getTopBarLabels(model)
        return model
    }

    fun getLoginLabels(model: Model): Model {
        model.addAttribute(getBasicsLabelsAndInfos(model))
        return model
    }

    fun getDashboardLabels(model: Model): Model {
        model.addAttribute(getBasicsLabelsAndInfos(model))
        model["title"] = Translator.toLocale("lang.dashboard")

        return model
    }

    fun getLoginPageLabels(model: Model): Model {
        model.addAttribute(getBasicsLabelsAndInfos(model))
        model["lang.welcomeBack"] = Translator.toLocale("lang.welcomeBack")
        model["lang.userNameOrPassWordIsWrong"] = Translator.toLocale("lang.userNameOrPassWordIsWrong")
        model["lang.userName"] = Translator.toLocale("lang.userName")
        model["lang.passWord"] = Translator.toLocale("lang.passWord")
        model["lang.rememberMe"] = Translator.toLocale("lang.rememberMe")
        model["lang.login"] = Translator.toLocale("lang.login")
        model["lang.forgotPassword"] = Translator.toLocale("lang.forgotPassword")
        model["lang.createAnAccount"] = Translator.toLocale("lang.createAnAccount")
        return model
    }

    fun getRegistrationPageLabels(model: Model): Model {
        model.addAttribute(getBasicsLabelsAndInfos(model))
        model["lang.createAnAccount"] = Translator.toLocale("lang.createAnAccount")
        model["lang.firstName"] = Translator.toLocale("lang.firstName")
        model["lang.lastName"] = Translator.toLocale("lang.lastName")
        model["lang.emailAddress"] = Translator.toLocale("lang.emailAddress")
        model["lang.passWord"] = Translator.toLocale("lang.passWord")
        model["lang.repeatePassWord"] = Translator.toLocale("lang.repeatePassWord")
        model["lang.registerAccount"] = Translator.toLocale("lang.registerAccount")
        model["lang.forgotPassword"] = Translator.toLocale("lang.forgotPassword")
        model["lang.alreadyHaveAnAccount"] = Translator.toLocale("lang.alreadyHaveAnAccount")
        return model
    }

    fun getLoginForgotPasswordLabels(model: Model): Model {
        model.addAttribute(getBasicsLabelsAndInfos(model))
        model["lang.forgotYourPassWord"] = Translator.toLocale("lang.forgotYourPassWord")
        model["lang.forgotYourPassWordDesc"] = Translator.toLocale("lang.forgotYourPassWordDesc")
        model["lang.enterEmail"] = Translator.toLocale("lang.enterEmail")
        model["lang.resetPassWord"] = Translator.toLocale("lang.resetPassWord")
        model["lang.createAnAccount"] = Translator.toLocale("lang.createAnAccount")
        model["lang.alreadyHaveAnAccount"] = Translator.toLocale("lang.alreadyHaveAnAccount")
        return model
    }

    fun getFrontendLabels(model: Model): Model {
        model.addAttribute(getBasicsLabelsAndInfos(model))

        return model
    }

    fun getArticlesLabels(model: Model): Model {
        model.addAttribute(getBasicsLabelsAndInfos(model))
        model["title"] = Translator.toLocale("lang.articles")
        model["lang.title"] = Translator.toLocale("lang.title")
        model["lang.author"] = Translator.toLocale("lang.author")
        model["lang.createdAt"] = Translator.toLocale("lang.createdAt")
        model["lang.previous"] = Translator.toLocale("lang.previous")
        model["lang.next"] = Translator.toLocale("lang.next")
        model["lang.addNew"] = Translator.toLocale("lang.addNew")
        return model
    }

    fun getArticleEditorLabels(model: Model): Model {
        model.addAttribute(getBasicsLabelsAndInfos(model))
        model["lang.delete"] = Translator.toLocale("lang.delete")
        model["lang.title"] = Translator.toLocale("lang.title")
        model["lang.content"] = Translator.toLocale("lang.content")
        model["lang.saveAsDraft"] = Translator.toLocale("lang.saveAsDraft")
        model["lang.general"] = Translator.toLocale("lang.general")
        model["lang.publish"] = Translator.toLocale("lang.publish")
        model["lang.featuredImage"] = Translator.toLocale("lang.featuredImage")
        model["lang.categories"] = Translator.toLocale("lang.categories")
        model["lang.tags"] = Translator.toLocale("lang.tags")
        model["lang.author"] = Translator.toLocale("lang.author")
        model["lang.excerpt"] = Translator.toLocale("lang.excerpt")
        model["title"] = Translator.toLocale("lang.editArticle")
        return model
    }

    fun getSiteLanguage(): String{
        return optionsRepository.findByName("sitelanguage").toString()
    }

    fun Options.render() = RenderOptions(
            name,
            value)

    data class RenderOptions(
            val name: String,
            val value: String)
}