package network.bobnet.cms.model.data

import network.bobnet.cms.repository.OptionsRepository
import network.bobnet.cms.util.Extensions
import org.springframework.http.HttpStatus

class HttpErrorMessages(var code: Int) {

    var message: String = when(code){
        /** 4xx **/
        HttpStatus.BAD_REQUEST.value() -> HttpStatus.BAD_REQUEST.reasonPhrase //400
        HttpStatus.UNAUTHORIZED.value() -> HttpStatus.UNAUTHORIZED.reasonPhrase //401
        HttpStatus.FORBIDDEN.value() -> HttpStatus.FORBIDDEN.reasonPhrase //403
        HttpStatus.NOT_FOUND.value() -> HttpStatus.NOT_FOUND.reasonPhrase //404
        HttpStatus.METHOD_NOT_ALLOWED.value() -> HttpStatus.METHOD_NOT_ALLOWED.reasonPhrase //405
        HttpStatus.NOT_ACCEPTABLE.value() -> HttpStatus.NOT_ACCEPTABLE.reasonPhrase //406
        HttpStatus.PROXY_AUTHENTICATION_REQUIRED.value() -> HttpStatus.PROXY_AUTHENTICATION_REQUIRED.reasonPhrase //407
        HttpStatus.REQUEST_TIMEOUT.value() -> HttpStatus.REQUEST_TIMEOUT.reasonPhrase //408
        HttpStatus.PRECONDITION_FAILED.value() -> HttpStatus.PRECONDITION_FAILED.reasonPhrase //412
        HttpStatus.URI_TOO_LONG.value() -> HttpStatus.URI_TOO_LONG.reasonPhrase //414
        HttpStatus.UNSUPPORTED_MEDIA_TYPE.value() -> HttpStatus.UNSUPPORTED_MEDIA_TYPE.reasonPhrase //415
        /** 5xx **/
        HttpStatus.INTERNAL_SERVER_ERROR.value() -> HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase //500
        HttpStatus.NOT_IMPLEMENTED.value() -> HttpStatus.NOT_IMPLEMENTED.reasonPhrase //501
        HttpStatus.BAD_GATEWAY.value() -> HttpStatus.BAD_GATEWAY.reasonPhrase //502
        HttpStatus.SERVICE_UNAVAILABLE.value() -> HttpStatus.SERVICE_UNAVAILABLE.reasonPhrase //503
        else -> "Error: $code"
    }
    private val extensions = Extensions()

    var slug: String = extensions.slugifyCode(message)
}