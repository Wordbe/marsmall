package co.white.marsmall.domain.user.entity

import co.white.marsmall.domain.common.entity.base.Audit
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class User(
    var name: String,

    @Column(unique = true)
    var email: String,

    var password: String,

    var phone: String?,

    @Enumerated(value = EnumType.STRING)
    var userRole: UserRole = UserRole.MEMBER
) : Audit() {

    constructor(
        id: Long? = null,
        email: String,
        name: String,
        password: String,
        phone: String?
    ) : this(name, email, password, phone) {
        this.id = id
    }

    fun modifyPassword(password: String) {
        this.password = password
    }
}

enum class UserRole {
    MANAGER,
    MEMBER
}
