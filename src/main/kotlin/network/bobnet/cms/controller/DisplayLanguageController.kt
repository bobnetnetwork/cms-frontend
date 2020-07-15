package network.bobnet.cms.controller

import network.bobnet.cms.config.TemplateVersionConfig
import network.bobnet.cms.model.Options
import network.bobnet.cms.repository.OptionsRepository
import network.bobnet.cms.repository.user.UserRepository
import network.bobnet.cms.util.LoggedInUser
import network.bobnet.cms.util.Translator
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.server.ResponseStatusException
import java.util.*


@Controller
class DisplayLanguageController(private val optionsRepository: OptionsRepository,
                                private val userRepository: UserRepository,
                                private val templateVersionConfig: TemplateVersionConfig) {

    private final val DASHBOARD = "lang.dashboard"
    private final val ARTICLES = "lang.articles"
    private final val CATEGORIES = "lang.categories"
    private final val TAGS = "lang.tags"
    private final val USERS = "lang.users"
    private final val SETTINGS = "lang.settings"
    private final val MEDIA = "lang.media"
    private final val PASSWORD = "lang.passWord"
    private final val FORGOT_PASSWORD = "lang.forgotPassword"
    private final val ALREADY_HAVE_AN_ACCOUNT = "lang.alreadyHaveAnAccount"
    private final val CREATE_AN_ACCOUNT = "lang.createAnAccount"
    private final val PREVIOUS = "lang.previous"
    private final val NEXT = "lang.next"
    private final val ADD_NEW = "lang.addNew"
    private final val TITLE = "lang.title"
    private final val AUTHOR = "lang.author"

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
        model[DASHBOARD] = Translator.toLocale(DASHBOARD)
        model["lang.contents"] = Translator.toLocale("lang.contents")
        model[ARTICLES] = Translator.toLocale(ARTICLES)
        model[CATEGORIES] = Translator.toLocale(CATEGORIES)
        model[TAGS] = Translator.toLocale(TAGS)
        model["lang.pages"] = Translator.toLocale("lang.pages")
        model["lang.aministration"] = Translator.toLocale("lang.aministration")
        model[USERS] = Translator.toLocale(USERS)
        model["lang.roles"] = Translator.toLocale("lang.roles")
        model[SETTINGS] = Translator.toLocale(SETTINGS)
        model[MEDIA] = Translator.toLocale(MEDIA)
        return model
    }

    fun getErrorPageLabels(model: Model): Model{
        getSiteInfo(model)
        return model
    }

    fun getSettingsLabels(model: Model): Model{
        getAdminBasicsLabelsAndInfos(model)
        model["title"] = Translator.toLocale(SETTINGS)
        return model
    }

    private fun getTopBarLabels(model: Model): Model {
        val user = LoggedInUser(userRepository).getUser()
        if (user != null) {
            model["loggedInUserFirstName"] = user.firstName
            model["loggedInUserLastName"] = user.lastName
            model["loggedInUserUserName"] = user.userName
            model["lang.logout"] = Translator.toLocale("lang.logout")
            model["lang.profile"] = Translator.toLocale("lang.profile")
            model["lang.readyToLeave"] = Translator.toLocale("lang.readyToLeave")
            model["lang.readyToLeaveDesc"] = Translator.toLocale("lang.readyToLeaveDesc")
            model["lang.cancel"] = Translator.toLocale("lang.cancel")
        }
        return model
    }

    private fun getBasicsLabelsAndInfos(model: Model): Model {
        getSiteInfo(model)
        getCSSJSVersions(model)
        return model
    }

    private fun getAdminBasicsLabelsAndInfos(model: Model): Model{
        getBasicsLabelsAndInfos(model)
        getSideBarLabels(model)
        getTopBarLabels(model)
        return model
    }

    fun getDashboardLabels(model: Model): Model {
        model.addAttribute(getAdminBasicsLabelsAndInfos(model))
        model["title"] = Translator.toLocale(DASHBOARD)

        return model
    }

    fun getCSSJSVersions(model: Model): Model{
        model["bootstrap-version"] = templateVersionConfig.bootstrap
        model["font-awesome-version"] = templateVersionConfig.fontAwesome
        model["jquery-version"] = templateVersionConfig.jquery
        model["tinymce-version"] = templateVersionConfig.tinymce
        model["chart-js-version"] = templateVersionConfig.chartJs
        model["jquery-easing-version"] = templateVersionConfig.jqueryEasing
        model["datatables-version"] = templateVersionConfig.datatables
        model["animate-css"] = templateVersionConfig.animateCSS
        model["aos"] = templateVersionConfig.aos
        model["waypoints"] = templateVersionConfig.waypoints
        model["owlCarousel"] = templateVersionConfig.owlCarousel
        model["isotopeLayout"] = templateVersionConfig.isotopeLayout
        return model
    }

    fun getLoginPageLabels(model: Model): Model {
        model.addAttribute(getBasicsLabelsAndInfos(model))
        model["lang.welcomeBack"] = Translator.toLocale("lang.welcomeBack")
        model["lang.userNameOrPassWordIsWrong"] = Translator.toLocale("lang.userNameOrPassWordIsWrong")
        model["lang.userName"] = Translator.toLocale("lang.userName")
        model[PASSWORD] = Translator.toLocale(PASSWORD)
        model["lang.rememberMe"] = Translator.toLocale("lang.rememberMe")
        model["lang.login"] = Translator.toLocale("lang.login")
        model[FORGOT_PASSWORD] = Translator.toLocale(FORGOT_PASSWORD)
        model[CREATE_AN_ACCOUNT] = Translator.toLocale(CREATE_AN_ACCOUNT)
        return model
    }

    fun getRegistrationPageLabels(model: Model): Model {
        model.addAttribute(getBasicsLabelsAndInfos(model))
        model[CREATE_AN_ACCOUNT] = Translator.toLocale(CREATE_AN_ACCOUNT)
        model["lang.firstName"] = Translator.toLocale("lang.firstName")
        model["lang.lastName"] = Translator.toLocale("lang.lastName")
        model["lang.emailAddress"] = Translator.toLocale("lang.emailAddress")
        model[PASSWORD] = Translator.toLocale(PASSWORD)
        model["lang.repeatePassWord"] = Translator.toLocale("lang.repeatePassWord")
        model["lang.registerAccount"] = Translator.toLocale("lang.registerAccount")
        model[FORGOT_PASSWORD] = Translator.toLocale(FORGOT_PASSWORD)
        model[ALREADY_HAVE_AN_ACCOUNT] = Translator.toLocale(ALREADY_HAVE_AN_ACCOUNT)
        return model
    }

    fun getLoginForgotPasswordLabels(model: Model): Model {
        model.addAttribute(getBasicsLabelsAndInfos(model))
        model["lang.forgotYourPassWord"] = Translator.toLocale("lang.forgotYourPassWord")
        model["lang.forgotYourPassWordDesc"] = Translator.toLocale("lang.forgotYourPassWordDesc")
        model["lang.enterEmail"] = Translator.toLocale("lang.enterEmail")
        model["lang.resetPassWord"] = Translator.toLocale("lang.resetPassWord")
        model[CREATE_AN_ACCOUNT] = Translator.toLocale(CREATE_AN_ACCOUNT)
        model[ALREADY_HAVE_AN_ACCOUNT] = Translator.toLocale(ALREADY_HAVE_AN_ACCOUNT)
        return model
    }

    fun getFrontendLabels(model: Model): Model {
        model.addAttribute(getBasicsLabelsAndInfos(model))
        model["lang.readMore"] = Translator.toLocale("lang.readMore")
        model["lang.recentPosts"] = Translator.toLocale("lang.recentPosts")
        return model
    }

    fun getContentLabels(model: Model): Model{
        model["lang.createdAt"] = Translator.toLocale("lang.createdAt")
        model[PREVIOUS] = Translator.toLocale(PREVIOUS)
        model[NEXT] = Translator.toLocale(NEXT)
        model[ADD_NEW] = Translator.toLocale(ADD_NEW)
        return model
    }

    fun getArticlesLabels(model: Model): Model {
        model.addAttribute(getAdminBasicsLabelsAndInfos(model))
        model.addAttribute(getContentLabels(model))
        model["title"] = Translator.toLocale(CATEGORIES)
        model[TITLE] = Translator.toLocale(TITLE)
        model[AUTHOR] = Translator.toLocale(AUTHOR)
        return model
    }

    fun getMediaLabels(model: Model): Model{
        model.addAttribute(getAdminBasicsLabelsAndInfos(model))
        model.addAttribute(getContentLabels(model))
        model["title"] = Translator.toLocale(MEDIA)
        model[TITLE] = Translator.toLocale(TITLE)
        model["lang.mimeType"] = Translator.toLocale("lang.mimeType")
        return model
    }

    fun getArticleEditorLabels(model: Model): Model {
        model.addAttribute(getAdminBasicsLabelsAndInfos(model))
        model["lang.delete"] = Translator.toLocale("lang.delete")
        model[TITLE] = Translator.toLocale(TITLE)
        model["lang.content"] = Translator.toLocale("lang.content")
        model["lang.saveAsDraft"] = Translator.toLocale("lang.saveAsDraft")
        model["lang.general"] = Translator.toLocale("lang.general")
        model["lang.publish"] = Translator.toLocale("lang.publish")
        model["lang.featuredImage"] = Translator.toLocale("lang.featuredImage")
        model[CATEGORIES] = Translator.toLocale(CATEGORIES)
        model[TAGS] = Translator.toLocale(TAGS)
        model[AUTHOR] = Translator.toLocale(AUTHOR)
        model["lang.excerpt"] = Translator.toLocale("lang.excerpt")
        model["title"] = Translator.toLocale("lang.editArticle")
        return model
    }


    fun getCategoriesLabels(model: Model): Model{
        model.addAttribute(getAdminBasicsLabelsAndInfos(model))
        model.addAttribute(getContentLabels(model))
        model["title"] = Translator.toLocale(ARTICLES)
        model[TITLE] = Translator.toLocale(TITLE)
        model[AUTHOR] = Translator.toLocale(AUTHOR)
        model["lang.parent"] = Translator.toLocale("lang.parent")
        return model
    }

    fun getUsersLabels(model: Model): Model {
        model.addAttribute(getBasicsLabelsAndInfos(model))
        model["title"] = Translator.toLocale(USERS)
        model["lang.name"] = Translator.toLocale("lang.name")
        model["lang.registeredAt"] = Translator.toLocale("lang.registeredAt")
        model[PREVIOUS] = Translator.toLocale(PREVIOUS)
        model[NEXT] = Translator.toLocale(NEXT)
        model[ADD_NEW] = Translator.toLocale(ADD_NEW)
        return model
    }

    fun Options.render() = RenderOptions(
            name,
            value)

    data class RenderOptions(
            val name: String,
            val value: String)
}
