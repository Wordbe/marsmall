package co.white.marsmall.common.security.token

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.KeyGenerator

@Component
class KeyManager {

    fun encryptSecretKey(encodedSecretKey: String): Key {
        val keyBytes = Decoders.BASE64.decode(encodedSecretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun refreshTokenSecretKey(): String {
        val randomString = RandomStringUtils.randomAlphanumeric(128)
        return Encoders.BASE64.encode(randomString.toByteArray())
    }
}
