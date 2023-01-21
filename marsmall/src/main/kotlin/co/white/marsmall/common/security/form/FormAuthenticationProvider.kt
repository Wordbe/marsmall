package co.white.marsmall.common.security.form

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class FormAuthenticationProvider(
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder,
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val rawPassword = authentication.credentials as String
        val user = userDetailsService.loadUserByUsername(username) as User
        val dbPassword = user.password

        if (!passwordEncoder.matches(rawPassword, dbPassword)) {
            throw BadCredentialsException("Password is incorrect")
        }

        return UsernamePasswordAuthenticationToken(user.username, user.password, user.authorities)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
