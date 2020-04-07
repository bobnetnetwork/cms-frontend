package network.bobnet.cms.security

import network.bobnet.cms.security.handler.RefererRedirectionAuthenticationSuccessHandler
import network.bobnet.cms.service.CustomUserDetailsService
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
open class WebSecurityConfiguration(private val customUserDetailsService: CustomUserDetailsService,
                                    private val passwordEncoderAndMatcher: PasswordEncoder,
                                    private val refererRedirectionAuthenticationSuccessHandler: RefererRedirectionAuthenticationSuccessHandler)
    : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.authorizeRequests()
                .antMatchers("/admin").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login")
                .and()
                .logout()
                .logoutUrl("/logout");
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoderAndMatcher)
    }
}