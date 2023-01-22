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
