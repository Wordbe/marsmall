package co.white.marsmall

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class MarsmallApplication

fun main(args: Array<String>) {
    runApplication<MarsmallApplication>(*args)
}
