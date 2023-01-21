package co.white.marsmall.common.security.form

import co.white.marsmall.domain.user.service.UserService
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Primary
@Service
class UserDetailsServiceImpl(
    private val userService: UserService,
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        return userService.findByEmail(email).run {
            User.builder()
                .username(this.email)
                .password(password)
                .roles(userRole.name)
                .build()
        }
    }
}
