package network.bobnet.cms.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*
import javax.servlet.http.HttpServletRequest


@Configuration
class CustomLocaleResolver : AcceptHeaderLocaleResolver(), WebMvcConfigurer {
    var locales: MutableList<Locale> = listOf(
            Locale("en"),
            Locale("fr")) as MutableList<Locale>

    override fun resolveLocale(request: HttpServletRequest): Locale {
        val headerLang = request.getHeader("Accept-Language")
        return if (headerLang == null || headerLang.isEmpty()) Locale.getDefault() else Locale.lookup(Locale.LanguageRange.parse(headerLang), locales)
    }

    @Bean
    fun messageSource(): ResourceBundleMessageSource {
        val rs = ResourceBundleMessageSource()
        rs.setBasename("messages")
        rs.setDefaultEncoding("UTF-8")
        rs.setUseCodeAsDefaultMessage(true)
        return rs
    }
}