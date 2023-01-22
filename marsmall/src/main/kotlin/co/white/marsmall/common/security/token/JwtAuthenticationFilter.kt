package co.white.marsmall.common.security.token

import co.white.marsmall.api.common.ErrorResponse
import com.google.gson.Gson
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

private val log = KotlinLogging.logger {}

@Component
class JwtAuthenticationFilter(
    private val jwtManager: JwtManager,
    private val gson: Gson,
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val accessToken = jwtManager.extractJwt(request)
        if (accessToken.isBlank()) {
            chain.doFilter(request, response)
            return
        }

        try {
            val jws = jwtManager.getJws(accessToken)
            setAuthentication(jws)
        } catch (e: Exception) {
            setErrorResponse(response, e)
            return
        }

        chain.doFilter(request, response)
    }

    private fun setAuthentication(jws: Jws<Claims>) {
        val email = jws.body.audience
        val role = jws.body["role"] as String
        val user = User.builder().username(email).password("").roles(role).build()
        val authentication = UsernamePasswordAuthenticationToken(email, "", user.authorities)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun setErrorResponse(response: HttpServletResponse, e: Exception) {
        val status = when (e) {
            is IllegalArgumentException -> HttpStatus.BAD_REQUEST
            is JwtException -> HttpStatus.UNAUTHORIZED
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        response.status = status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        val errorResponse = ErrorResponse(message = e.message)
        log.error(e) { errorResponse }
        response.writer.print(gson.toJson(errorResponse))
    }
}
