package co.white.marsmall.api.common

import co.white.marsmall.common.exception.UnauthorizedException
import io.jsonwebtoken.JwtException
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

private val log = logger {}

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun handleBadRequest(e: RuntimeException): ResponseEntity<ErrorResponse> {
        return errorResponseEntity(e, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFound(e: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        return errorResponseEntity(e, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(JwtException::class)
    fun handleJwt(e: JwtException): ResponseEntity<ErrorResponse> {
        return errorResponseEntity(e, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleJwt(e: UnauthorizedException): ResponseEntity<ErrorResponse> {
        return errorResponseEntity(e, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleUnknown(e: RuntimeException): ResponseEntity<ErrorResponse> {
        return errorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun errorResponseEntity(e: RuntimeException, status: HttpStatus): ResponseEntity<ErrorResponse> {
        log.error(e) { e.message }
        return ResponseEntity.status(status)
            .body(ErrorResponse(e.message))
    }
}

