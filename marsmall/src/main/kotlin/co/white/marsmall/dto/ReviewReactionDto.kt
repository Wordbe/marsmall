package co.white.marsmall.dto

import co.white.marsmall.domain.review.entity.ReviewReactionType

data class ReviewReactionRequest(
    val userId: Long,
    val reviewId: Long,
    val type: ReviewReactionType
)

data class ReviewReactionCountRequest(
    val reviewId: Long,
    val type: ReviewReactionType
)

data class ReviewReactionCountResponse(
    val count: Long
)
