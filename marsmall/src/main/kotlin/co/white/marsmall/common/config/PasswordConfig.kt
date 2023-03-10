package co.white.marsmall.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
}
