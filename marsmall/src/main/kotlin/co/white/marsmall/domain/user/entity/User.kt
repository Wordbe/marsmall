package co.white.marsmall.domain.user.entity

import co.white.marsmall.domain.common.entity.base.Audit
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate

@DynamicUpdate
@Entity
@Table(indexes = [Index(name = "idx_user_email", columnList = "email", unique = true)])
class User(
    id: Long? = null,

    var name: String,

    var email: String,

    var password: String,

    var phone: String?,

    @Enumerated(value = EnumType.STRING)
    var userRole: UserRole = UserRole.MEMBER,

    var secretKey: String? = null
) : Audit(id = id) {

    fun modifyPassword(password: String) {
        this.password = password
    }

    fun modifySecretKey(secretKey: String?) {
        this.secretKey = secretKey
    }
}

enum class UserRole {
    MANAGER,
    MEMBER
}
