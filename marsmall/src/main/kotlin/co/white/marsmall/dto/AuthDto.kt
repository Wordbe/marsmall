package co.white.marsmall.dto

data class AuthLoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val accessToken: String,
)
