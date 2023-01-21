package co.white.marsmall.dto

import co.white.marsmall.domain.user.entity.User
import co.white.marsmall.domain.user.entity.UserRole

data class UserRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String?,
    val userRole: UserRole?
) {

    fun toEntity() = User(
        name = name,
        email = email,
        password = password,
        phone = phone,
        userRole = userRole ?: UserRole.MEMBER
    )
}

data class UserResponse(
    val id: Long?,
    val name: String,
    val email: String,
    val phone: String?,
    val userRole: UserRole,
) {
    companion object {
        operator fun invoke(u: User) = UserResponse(
            id = u.id,
            name = u.name,
            email = u.email,
            phone = u.phone,
            userRole = u.userRole
        )
    }
}
