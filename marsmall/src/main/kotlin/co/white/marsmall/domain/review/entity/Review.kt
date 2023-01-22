package co.white.marsmall.domain.review.entity

import co.white.marsmall.domain.common.entity.base.Audit
import jakarta.persistence.Entity

@Entity
class Review(
    id: Long? = null,
    var userId: Long,
    var rate: Int,
    var content: String
) : Audit(id = id)
