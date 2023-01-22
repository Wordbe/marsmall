package co.white.marsmall.common.security.token

import co.white.marsmall.domain.user.entity.User
import co.white.marsmall.dto.TokenPayLoad
import com.google.gson.Gson
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class JwtManager(
    private val jwtProperty: JwtProperty,
    private val keyManager: KeyManager,
    private val gson: Gson,
) {
    val accessTokenSecretKey: Key by lazy {
        keyManager.encryptSecretKey(jwtProperty.encodedSecretKey)
    }

    fun issueAccessToken(user: User, issuedAt: LocalDateTime) =
        issueJwt(user, issuedAt, issuedAt.plusMinutes(jwtProperty.accessTokenValidMinutes), accessTokenSecretKey)

    fun issueRefreshToken(user: User, issuedAt: LocalDateTime, key: Key) =
        issueJwt(user, issuedAt, issuedAt.plusDays(jwtProperty.refreshTokenValidDays), key)

    fun issueJwt(user: User, issuedAt: LocalDateTime, expiredAt: LocalDateTime, key: Key): String {
        val claims = Jwts.claims()
        claims.audience = user.email
        claims["role"] = user.userRole
        claims.issuedAt = toDate(issuedAt)
        claims.expiration = toDate(expiredAt)

        return Jwts.builder()
            .setHeaderParam("typ", Header.JWT_TYPE)
            .setClaims(claims)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getJws(jwt: String, key: Key): Jws<Claims> {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(jwt)
    }

    fun getJws(jwt: String): Jws<Claims> {
        return getJws(jwt, accessTokenSecretKey)
    }

    fun getPayload(jwt: String): TokenPayLoad {
        val chunks = jwt.split(".")
        val payload = String(Decoders.BASE64.decode(chunks[1]))
        return gson.fromJson(payload, TokenPayLoad::class.java)
    }

    fun extractJwt(req: HttpServletRequest): String {
        val auth = req.getHeader(HttpHeaders.AUTHORIZATION)
        if (auth != null && auth.startsWith("Bearer ")) {
            return auth.substring(7)
        }

        return ""
    }

    private fun toDate(localDateTime: LocalDateTime): Date =
        Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
}
