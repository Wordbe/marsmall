package co.white.marsmall.dto

import co.white.marsmall.domain.user.entity.User

data class UserRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String?,
) {

    fun toEntity() = User(
        name = name,
        email = email,
        password = password,
        phone = phone
    )
}

data class UserResponse(
    val id: Long?,
    val name: String,
    val email: String,
    val password: String,
    val phone: String?,
) {
    companion object {
        operator fun invoke(u: User) = UserResponse(
            id = u.id,
            name = u.name,
            email = u.email,
            password = u.password,
            phone = u.phone
        )
    }
}
