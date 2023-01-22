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
        ReviewReactionCountResponse(reviewReactionRepository.countByReviewIdAndTypeAndDeletedFalse(reviewId, type))

    @Transactional
    fun toggle(r: ReviewReactionRequest) {
        val reaction = reviewReactionRepository.findByUserIdAndReviewIdAndType(r.userId, r.reviewId, r.type)

        if (reaction == null) {
            val user = userService.findById(r.userId)
            val review = reviewService.findById(r.reviewId)
            reviewReactionRepository.save(ReviewReaction(user = user, review = review, type = r.type))
            return
        }

        // 여기서 항상 user, review 쿼리도 같이 실행된다. 해당 id 의 user, review 를 확인하는 로직으로 생각하자. (foreign key check)
        reaction.toggle()
    }
}
