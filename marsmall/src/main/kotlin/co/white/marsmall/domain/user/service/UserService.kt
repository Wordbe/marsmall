package co.white.marsmall.domain.user.service

import co.white.marsmall.domain.auth.aspect.AuthAspect
import co.white.marsmall.domain.user.entity.User
import co.white.marsmall.domain.user.repository.UserRepository
import co.white.marsmall.dto.UserModifyRequest
import co.white.marsmall.dto.UserResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authAspect: AuthAspect,
) {
    fun findByEmail(email: String): User =
        userRepository.findByEmail(email) ?: throw EntityNotFoundException("No user exists. email: $email")

    fun findById(id: Long): User =
        userRepository.findByIdOrNull(id) ?: throw EntityNotFoundException("No user exists. id: $id")

    fun create(user: User): UserResponse {
        user.modifyPassword(passwordEncoder.encode(user.password))
        return UserResponse(userRepository.save(user))
    }

    fun getById(id: Long): UserResponse =
        UserResponse(findById(id))

    @Transactional
    fun modify(id: Long, request: UserModifyRequest): UserResponse {
        val modifiedUser = findById(id).apply {
            password = passwordEncoder.encode(request.password)
        }
        return UserResponse(modifiedUser)
    }

    fun getMe(): UserResponse =
        UserResponse(findByEmail(authAspect.userEmail()))

    @Transactional
    fun modifyMe(request: UserModifyRequest): UserResponse {
        val modifiedUser = findByEmail(authAspect.userEmail()).apply {
            password = passwordEncoder.encode(request.password)
        }
        return UserResponse(modifiedUser)
    }

    fun isEmailUnique(email: String): Boolean {
        return userRepository.findByEmail(email) == null
    }

    fun checkPassword(user: User, rawPassword: String) {
        if (!passwordEncoder.matches(rawPassword, user.password)) {
            throw IllegalStateException("Password does NOT match.")
        }
    }
}
