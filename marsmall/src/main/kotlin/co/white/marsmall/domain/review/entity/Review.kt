package co.white.marsmall.domain.review.entity

import co.white.marsmall.domain.common.entity.base.Audit
import jakarta.persistence.Entity

@Entity
class Review(
    var userId: Long,
    var title: String,
    var content: String
) : Audit() {

    constructor(id: Long? = null, userId: Long, tile: String, content: String) : this(userId, tile, content) {
        this.id = id
    }
}
