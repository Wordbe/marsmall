package co.white.marsmall.api

import co.white.marsmall.domain.auth.service.AuthService
import co.white.marsmall.dto.AuthLoginRequest
import co.white.marsmall.dto.AuthResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/auth/login")
    fun login(@RequestBody request: AuthLoginRequest,
              req: HttpServletRequest,
              res: HttpServletResponse): AuthResponse {
        return authService.login(request, req, res)
    }

    @PostMapping("/auth/refresh")
    fun refresh(req: HttpServletRequest, res: HttpServletResponse): AuthResponse {
        return authService.refresh(req, res)
    }
}
