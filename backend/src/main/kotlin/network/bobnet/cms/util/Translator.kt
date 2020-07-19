package network.bobnet.cms.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.stereotype.Component


@Component
class Translator @Autowired internal constructor(messageSource: ResourceBundleMessageSource?) {
    companion object {
        private var messageSource: ResourceBundleMessageSource? = null
        fun toLocale(msg: String?): String {
            val locale = LocaleContextHolder.getLocale()
            return messageSource!!.getMessage(msg!!, null, locale)
        }
    }

    init {
        Companion.messageSource = messageSource
    }
}