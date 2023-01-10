package co.white.marsmall.domain.review.entity

import co.white.marsmall.domain.common.entity.base.Audit
import jakarta.persistence.Entity

@Entity
class Review(
    var userId: Long,
    var rate: Int,
    var content: String
) : Audit() {

    constructor(id: Long? = null, userId: Long, rate: Int, content: String) : this(userId, rate, content) {
        this.id = id
    }
}
