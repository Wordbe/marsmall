package co.white.marsmall

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication(exclude = [UserDetailsServiceAutoConfiguration::class])
class MarsmallApplication

fun main(args: Array<String>) {
    runApplication<MarsmallApplication>(*args)
}
