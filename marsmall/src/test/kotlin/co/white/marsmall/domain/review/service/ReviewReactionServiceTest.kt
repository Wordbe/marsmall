package co.white.marsmall.domain.review.service

import co.white.marsmall.domain.review.entity.ReviewReactionType.LIKE
import co.white.marsmall.domain.review.repository.ReviewReactionRepository
import co.white.marsmall.domain.user.service.UserService
import co.white.marsmall.dto.ReviewReactionCountResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ReviewReactionServiceTest : BehaviorSpec({
    val reviewReactionRepository = mockk<ReviewReactionRepository>()
    val userService = mockk<UserService>()
    val reviewService = mockk<ReviewService>()
    val reviewReactionService = ReviewReactionService(reviewReactionRepository, userService, reviewService)

    Given("countByUserId") {
        every { reviewReactionRepository.countByReviewIdAndType(1, LIKE) } returns 12

        reviewReactionService.countByReviewIdAndType(1, LIKE) shouldBe ReviewReactionCountResponse(12)
    }
})
