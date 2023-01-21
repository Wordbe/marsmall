package co.white.marsmall.api

import co.white.marsmall.domain.user.service.UserService
import co.white.marsmall.dto.UserRequest
import co.white.marsmall.dto.UserResponse
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/user") // signup
    fun create(@RequestBody userRequest: UserRequest): UserResponse {
        return userService.create(userRequest.toEntity())
    }

    @GetMapping("/user/{id}")
    fun get(@PathVariable id: Long): UserResponse {
        return userService.getById(id)
    }

    @GetMapping("/user")
    fun isEmailUnique(email: String) : Boolean {
        return userService.isEmailUnique(email)
    }
}
