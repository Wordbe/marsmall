package co.white.marsmall.domain.auth.service

import co.white.marsmall.common.security.token.JwtManager
import co.white.marsmall.common.security.token.KeyManager
import co.white.marsmall.domain.user.service.UserService
import co.white.marsmall.dto.AuthLoginRequest
import co.white.marsmall.dto.AuthLoginResponse
import co.white.marsmall.dto.AuthRefreshRequest
import co.white.marsmall.dto.AuthRefreshResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class AuthService(
    private val userService: UserService,
    private val jwtManager: JwtManager,
    private val keyManager: KeyManager,
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
        val payload = jwtManager.getClaimsNotVerified(request.refreshToken)
        val user = userService.findByEmail(payload.email)
        val encodedSecretKey = user.secretKey ?: throw IllegalStateException("No SecretKey exists.")
        jwtManager.getJws(request.refreshToken, keyManager.encryptSecretKey(encodedSecretKey))

        val accessToken = jwtManager.issueAccessToken(user, LocalDateTime.now())
        return AuthRefreshResponse(accessToken)
    }

    private fun extractAccessToken(req: HttpServletRequest): String {
        val authorization = req.getHeader("Authorization")
            ?: throw IllegalArgumentException("No 'Authorization' header exists.")

        if (authorization.startsWith("Bearer ")) {
            return authorization.substring(7)
        }

        throw IllegalArgumentException("'Authorization' header is incorrect.")
    }
}
