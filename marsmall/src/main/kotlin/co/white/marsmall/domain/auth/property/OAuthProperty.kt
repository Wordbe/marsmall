package co.white.marsmall.domain.auth.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth.kakao")
data class OAuthProperty(
    val restApiKey: String,
    val redirectUri: String,
)