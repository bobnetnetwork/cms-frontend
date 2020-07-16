package network.bobnet.cms.service.administration

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LogService(val name: Class<Any>){
    val logger: Logger = LoggerFactory.getLogger(name)

    fun info(message: String){
        logger.info(message)
    }

    fun warn(message: String){
        logger.warn(message)
    }

    fun error(message: String){
        logger.error(message)
    }

    fun debug(message: String){
        logger.debug(message)
    }
}