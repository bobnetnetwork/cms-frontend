package network.bobnet.cms.controller.backoffice

import network.bobnet.cms.service.LogService
import org.springframework.stereotype.Controller

@Controller
class TagsController {

    private val logger: LogService = LogService(this.javaClass)

    private final val ROW_PER_PAGE: Int = 5

    private final val TAGS_TEMPLATE = "backoffice/tags"
    private final val TAG_TEMPLATE = "backoffice/tag"
}
