package co.white.marsmall.domain.auth.service

import co.white.marsmall.common.security.token.JwtManager
import co.white.marsmall.common.security.token.KeyManager
import co.white.marsmall.domain.auth.property.OAuthProperty
import co.white.marsmall.domain.user.service.UserService
import co.white.marsmall.dto.*
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime

@Service
class AuthService(
    private val userService: UserService,
    private val jwtManager: JwtManager,
    private val keyManager: KeyManager,
    private val oAuthProperty: OAuthProperty,
    private val restTemplate: RestTemplate,
) {

    @Transactional
    fun login(authLoginRequest: AuthLoginRequest, res: HttpServletResponse): AuthLoginResponse {
        val user = userService.findByEmail(authLoginRequest.email)
        userService.checkPassword(user, authLoginRequest.password)

        val secretKey = keyManager.refreshTokenSecretKey()
        user.modifySecretKey(secretKey)

        val now = LocalDateTime.now()
        val accessToken = jwtManager.issueAccessToken(user, now)
        val refreshToken = jwtManager.issueRefreshToken(user, now, keyManager.encryptSecretKey(secretKey))
        return AuthLoginResponse(accessToken, refreshToken)
    }

    fun refreshAccessToken(request: AuthRefreshRequest): AuthRefreshResponse {
        val payload = jwtManager.getPayload(request.refreshToken)
        val user = userService.findByEmail(payload.email)
        val encodedSecretKey = user.secretKey ?: throw IllegalStateException("No SecretKey exists.")
        jwtManager.getJws(request.refreshToken, keyManager.encryptSecretKey(encodedSecretKey))

        val accessToken = jwtManager.issueAccessToken(user, LocalDateTime.now())
        return AuthRefreshResponse(accessToken)
    }

    fun oauthLoginRedirectUri(): String {
        return "https://kauth.kakao.com/oauth/authorize?client_id=${oAuthProperty.restApiKey}&redirect_uri=${oAuthProperty.redirectUri}&response_type=code"
    }

    fun getToken(code: String): KakaoOauthTokenResponse? {
        val uri = "https://kauth.kakao.com/oauth/token"

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val body = LinkedMultiValueMap(
            mapOf(
                "grant_type" to listOf("authorization_code"),
                "client_id" to listOf(oAuthProperty.restApiKey),
                "redirect_uri" to listOf(oAuthProperty.redirectUri),
                "code" to listOf(code),
            )
        )

        return restTemplate.postForObject(uri, HttpEntity(body, headers), KakaoOauthTokenResponse::class.java)
    }
}
