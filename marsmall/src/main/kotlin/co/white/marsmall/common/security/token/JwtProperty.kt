package co.white.marsmall.common.security.token

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperty(
    val encodedSecretKey: String,
    val accessTokenValidMinutes: Long,
    val refreshTokenValidDays: Long,
)
