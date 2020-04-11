package network.bobnet.cms.security

import network.bobnet.cms.repository.user.UserRepository
import network.bobnet.cms.util.LoggedInUser
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.RequestCache
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomSimpleUrlAuthenticationSuccessHandler(private val userRepository: UserRepository): AuthenticationSuccessHandler, SimpleUrlAuthenticationSuccessHandler() {
    private val  requestCache: RequestCache = HttpSessionRequestCache()

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {

        val savedRequest = requestCache.getRequest(request, response)

        if (savedRequest == null) {

            onAuthenticationSuccess(request, response, authentication)
            return
        }
        val targetUrlParameter = targetUrlParameter
        if (request != null) {
            if(response != null){
                LoggedInUser(userRepository).setUser(response)
            }
            if (isAlwaysUseDefaultTargetUrl
                    || targetUrlParameter != null && StringUtils.hasText(request
                            .getParameter(targetUrlParameter))) {
                requestCache.removeRequest(request, response)
                onAuthenticationSuccess(request, response, authentication)
                return
            }
        }
        clearAuthenticationAttributes(request)

        // Use the DefaultSavedRequest URL

        // Use the DefaultSavedRequest URL
        val targetUrl = savedRequest.redirectUrl
        logger.debug("Redirecting to DefaultSavedRequest Url: $targetUrl")
        redirectStrategy.sendRedirect(request, response, targetUrl)

    }

}
