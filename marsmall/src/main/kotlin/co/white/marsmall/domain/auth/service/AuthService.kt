package co.white.marsmall.domain.auth.service

import co.white.marsmall.domain.user.service.UserService
import co.white.marsmall.dto.AuthLoginRequest
import co.white.marsmall.dto.AuthResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
) {
    fun login(request: AuthLoginRequest, req: HttpServletRequest, res: HttpServletResponse): AuthResponse {
        val user = userService.findByEmail(request.email)
        userService.checkPassword(user, request.password)

        val accessToken = "eyl"
        return AuthResponse(accessToken)
    }

    fun refresh(req: HttpServletRequest, res: HttpServletResponse): AuthResponse {
        val accessToken = "eyl"
        return AuthResponse(accessToken)
    }
}
