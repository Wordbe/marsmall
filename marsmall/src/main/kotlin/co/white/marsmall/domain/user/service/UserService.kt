package co.white.marsmall.domain.user.service

import co.white.marsmall.domain.user.entity.User
import co.white.marsmall.domain.user.repository.UserRepository
import co.white.marsmall.dto.UserResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun findById(id: Long): User =
        userRepository.findByIdOrNull(id) ?: throw EntityNotFoundException("No user exists. id: $id")

    fun getById(id: Long): UserResponse =
        UserResponse(findById(id))

    fun create(user: User): UserResponse =
        UserResponse(userRepository.save(user))
}
