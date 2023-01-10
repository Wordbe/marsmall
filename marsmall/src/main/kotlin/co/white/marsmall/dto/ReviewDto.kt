package co.white.marsmall.dto

import co.white.marsmall.domain.review.entity.Review

data class ReviewRequest(
    val userId: Long,
    val rate: Int,
    val content: String
) {

    fun toEntity() = Review(
        userId = userId,
        rate = rate,
        content = content
    )
}

data class ReviewResponse(
    val userId: Long,
    val rate: Int,
    val content: String
) {
    companion object {
        operator fun invoke(r: Review) = ReviewResponse(
            userId = r.userId,
            rate = r.rate,
            content = r.content
        )
    }
}
