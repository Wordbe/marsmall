package co.white.marsmall.domain.review.repository

import co.white.marsmall.domain.review.entity.ReviewReaction
import co.white.marsmall.domain.review.entity.ReviewReactionType
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewReactionRepository : JpaRepository<ReviewReaction, Long> {
    fun countByReviewIdAndType(reviewId: Long, type: ReviewReactionType): Long

    fun findByUserIdAndReviewIdAndType(userId: Long, reviewId: Long, type: ReviewReactionType): ReviewReaction?
}
