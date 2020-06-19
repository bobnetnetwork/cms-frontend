package network.bobnet.cms.security

import network.bobnet.cms.repository.user.UserRepository
import network.bobnet.cms.service.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AuthenticationSuccessHandler


@EnableWebSecurity
class WebSecurityConfiguration(private val customUserDetailsService: CustomUserDetailsService,
                               private val passwordEncoderAndMatcher: PasswordEncoder,
                               private val userRepository: UserRepository)
    : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.authorizeRequests()
                .antMatchers("/admin/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login")
                //.successHandler(SavedRequestAwareAuthenticationSuccessHandler())
                .successHandler(loginSuccessHandler())
                .and()
                .logout()
                .deleteCookies("logged_in_user")
                .logoutUrl("/logout")
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoderAndMatcher)
    }

    /*@Bean
    fun logoutSuccessHandler(): LogoutSuccessHandler? {
        return CustomLogoutSuccessHandler()
    }

    @Bean
    fun accessDeniedHandler(): AccessDeniedHandler? {
        return CustomAccessDeniedHandler()
    }

    @Bean
    fun authenticationFailureHandler(): AuthenticationFailureHandler? {
        return CustomAuthenticationFailureHandler()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }*/

    @Bean
    fun loginSuccessHandler(): AuthenticationSuccessHandler? {
        return CustomSimpleUrlAuthenticationSuccessHandler(userRepository)
    }
}