package co.white.marsmall.domain.review.entity

import co.white.marsmall.domain.common.entity.base.Audit
import co.white.marsmall.domain.user.entity.User
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class ReviewReaction(
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    var review: Review,

    var type: ReviewReactionType,
    private var deleted: Boolean = false
) : Audit() {

    constructor(
        id: Long? = null,
        user: User,
        review: Review,
        type: ReviewReactionType,
        deleted: Boolean = false) : this(user, review, type, deleted) {
        this.id = id
    }

    fun toggle() {
        deleted = !deleted
    }
}
