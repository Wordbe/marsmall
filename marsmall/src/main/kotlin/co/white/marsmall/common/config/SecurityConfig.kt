package co.white.marsmall.common.config

import co.white.marsmall.common.security.token.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable() // No cookie, No CSRF
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Remove JSESSIONID
            .and()
            // 기본적으로 모든 요청을 허용하고, JWT 가 포함된 요청은 필터에서 유효성 검증 후 개별 컨트롤러에서 JWT 존재 검증
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}
