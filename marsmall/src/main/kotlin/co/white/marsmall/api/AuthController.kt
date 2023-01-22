package co.white.marsmall.api

import co.white.marsmall.domain.auth.service.AuthService
import co.white.marsmall.dto.AuthLoginRequest
import co.white.marsmall.dto.AuthRefreshRequest
import co.white.marsmall.dto.AuthLoginResponse
import co.white.marsmall.dto.AuthRefreshResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/auth/login")
    fun login(@RequestBody authLoginRequest: AuthLoginRequest, res: HttpServletResponse): AuthLoginResponse {
        return authService.login(authLoginRequest, res)
    }

    @PostMapping("/auth/refresh")
    fun refresh(@RequestBody authRefreshRequest: AuthRefreshRequest): AuthRefreshResponse {
        return authService.refreshAccessToken(authRefreshRequest)
    }
}
