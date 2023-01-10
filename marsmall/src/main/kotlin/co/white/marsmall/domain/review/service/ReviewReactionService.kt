package co.white.marsmall.domain.review.service

import co.white.marsmall.domain.review.entity.ReviewReaction
import co.white.marsmall.domain.review.entity.ReviewReactionType
import co.white.marsmall.domain.review.repository.ReviewReactionRepository
import co.white.marsmall.domain.user.service.UserService
import co.white.marsmall.dto.ReviewReactionCountResponse
import co.white.marsmall.dto.ReviewReactionRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewReactionService(
    private val reviewReactionRepository: ReviewReactionRepository,
    private val userService: UserService,
    private val reviewService: ReviewService
) {

    fun countByReviewIdAndType(reviewId: Long, type: ReviewReactionType): ReviewReactionCountResponse =
        ReviewReactionCountResponse(reviewReactionRepository.countByReviewIdAndType(reviewId, type))

    @Transactional
    fun toggle(r: ReviewReactionRequest) {
        val reaction = reviewReactionRepository.findByUserIdAndReviewIdAndType(r.userId, r.reviewId, r.type)

        if (reaction == null) {
            val user = userService.findById(r.userId)
            val review = reviewService.findById(r.reviewId)
            reviewReactionRepository.save(ReviewReaction(user, review, r.type))
            return
        }

        reaction.toggle()
    }
}
