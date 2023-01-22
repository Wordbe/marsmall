package co.white.marsmall.domain.review.entity

import co.white.marsmall.domain.common.entity.base.Audit
import co.white.marsmall.domain.user.entity.User
import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY

@Entity
class ReviewReaction(
    id: Long? = null,

    @ManyToOne(fetch = LAZY, )
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    var review: Review,

    @Enumerated(value = EnumType.STRING)
    var type: ReviewReactionType,

    private var deleted: Boolean = false
) : Audit(id = id) {

    fun toggle() {
        deleted = !deleted
    }
}
