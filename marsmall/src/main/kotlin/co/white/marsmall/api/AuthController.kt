package co.white.marsmall.api

import co.white.marsmall.domain.auth.service.AuthService
import co.white.marsmall.dto.*
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
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

    @GetMapping("/oauth/login")
    fun oauthLogin(httpServletResponse: HttpServletResponse) {
        httpServletResponse.sendRedirect(authService.oauthLoginRedirectUri())
    }

    @GetMapping("/login/oauth2/code/kakao")
    fun oauthLoginCallback(code: String): KakaoOauthTokenResponse? {
        return authService.getToken(code)
    }
}
