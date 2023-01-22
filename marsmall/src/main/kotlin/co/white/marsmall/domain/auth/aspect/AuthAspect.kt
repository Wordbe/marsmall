package co.white.marsmall.domain.auth.aspect

import co.white.marsmall.common.exception.UnauthorizedException
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Aspect
@Component
class AuthAspect {

    @Before("@annotation(co.white.marsmall.domain.auth.aspect.CheckLogin)")
    fun checkLogin() {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is String && principal == "anonymousUser") {
            throw UnauthorizedException("No principal exists.")
        }
    }

    fun userEmail(): String {
        return SecurityContextHolder.getContext().authentication.principal as String
    }
}
