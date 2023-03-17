package co.white.marsmall.dto

import co.white.marsmall.domain.user.entity.UserRole
import com.google.gson.annotations.SerializedName

data class AuthLoginRequest(
    val email: String,
    val password: String
)

data class AuthRefreshRequest(
    val refreshToken: String
)

data class AuthLoginResponse(
    val accessToken: String,
    val refreshToken: String,
)

data class AuthRefreshResponse(
    val accessToken: String,
)

data class TokenPayLoad(
    @SerializedName("aud")
    val email: String,
    val role: UserRole
)

data class KakaoOauthTokenRequest(
    val grantType: String,
    val clientId: String,
    val redirectUri: String,
    val code: String,
)

data class KakaoOauthTokenResponse(
    val token_type: String?,
    val access_token: String?,
    val id_token: String?,
    val expires_in: Int?,
    val refresh_token: String?,
    val refresh_token_expires_in: Int?,
    val scope: String?,
)
