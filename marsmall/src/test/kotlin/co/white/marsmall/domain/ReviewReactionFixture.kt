package co.white.marsmall.domain

import co.white.marsmall.domain.review.entity.Review
import co.white.marsmall.domain.review.entity.ReviewReaction
import co.white.marsmall.domain.review.entity.ReviewReactionType
import co.white.marsmall.domain.review.entity.ReviewReactionType.LIKE
import co.white.marsmall.domain.user.entity.User

fun reviewReaction(
    id: Long? = null,
    user: User = user(1),
    review: Review = review(2),
    type: ReviewReactionType = LIKE,
    deleted: Boolean = false
) = ReviewReaction(id, user, review, type, deleted)
