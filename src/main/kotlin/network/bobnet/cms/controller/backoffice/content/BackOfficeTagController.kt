package network.bobnet.cms.controller.backoffice.content

import network.bobnet.cms.service.administration.LogService
import org.springframework.stereotype.Controller

@Controller
class BackOfficeTagController {

    private val logger: LogService = LogService(this.javaClass)

    private final val ROW_PER_PAGE: Int = 5

    private final val TAGS_TEMPLATE = "backoffice/content/tags"
    private final val TAG_TEMPLATE = "backoffice/content/tag"
}
