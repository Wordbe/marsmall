package co.white.marsmall.domain.auth.service

import co.white.marsmall.common.security.token.JwtManager
import co.white.marsmall.common.security.token.KeyManager
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
    private val restTemplate: RestTemplate,
) {
    companion object {
        const val REST_API_KEY = "8c6fafe6d799a260a9d6922ec76b6d4e"
        const val REDIRECT_URI = "http://localhost:8080/login/oauth2/code/kakao"
    }

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
        return "https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code"
    }

    fun getToken(code: String): KakaoOauthTokenResponse? {
        val uri = "https://kauth.kakao.com/oauth/token"

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val body = LinkedMultiValueMap(mapOf(
            "grant_type" to listOf("authorization_code"),
            "client_id" to listOf(REST_API_KEY),
            "redirect_uri" to listOf(REDIRECT_URI),
            "code" to listOf(code),
        ))

        val httpEntity = HttpEntity(body, headers)

        println(uri)
        println(httpEntity)

        val response = restTemplate.postForObject(uri, httpEntity, KakaoOauthTokenResponse::class.java)
        println(response)

        return response
    }
}
