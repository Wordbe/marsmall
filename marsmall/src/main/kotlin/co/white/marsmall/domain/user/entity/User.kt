package co.white.marsmall.domain.user.entity

import co.white.marsmall.domain.common.entity.base.Audit
import jakarta.persistence.Entity

@Entity
class User(
    var name: String,
    var email: String,
    var password: String,
    var phone: String?,
) : Audit() {

    constructor(
        id: Long? = null,
        email: String,
        name: String,
        password: String,
        phone: String?) : this(name, email, password, phone) {
        this.id = id
    }
}
