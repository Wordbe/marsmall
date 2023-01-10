package co.white.marsmall.domain.review.service

import co.white.marsmall.domain.review
import co.white.marsmall.domain.review.entity.ReviewReactionType.LIKE
import co.white.marsmall.domain.review.repository.ReviewReactionRepository
import co.white.marsmall.domain.reviewReaction
import co.white.marsmall.domain.user
import co.white.marsmall.domain.user.service.UserService
import co.white.marsmall.dto.ReviewReactionCountResponse
import co.white.marsmall.dto.ReviewReactionRequest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewReactionServiceTest : BehaviorSpec({
    val reviewReactionRepository = mockk<ReviewReactionRepository>()
    val userService = mockk<UserService>()
    val reviewService = mockk<ReviewService>()
    val reviewReactionService = ReviewReactionService(reviewReactionRepository, userService, reviewService)

    Given("countByUserId") {
        every { reviewReactionRepository.countByReviewIdAndTypeAndDeletedFalse(1, LIKE) } returns 12

        reviewReactionService.countByReviewIdAndType(1, LIKE) shouldBe ReviewReactionCountResponse(12)
    }

    Given("toggle") {
        val request = ReviewReactionRequest(1, 2, LIKE)
        every { userService.findById(1) } returns user(1)
        every { reviewService.findById(2) } returns review(2)

        When("save first reaction") {
            every { reviewReactionRepository.findByUserIdAndReviewIdAndType(1, 2, LIKE) } returns null
            every { reviewReactionRepository.save(any()) } returns reviewReaction()

            Then("reaction saved") {
                withContext(Dispatchers.IO) {
                    reviewReactionService.toggle(request)
                }
                verify(exactly = 1) { reviewReactionRepository.save(any()) }
            }
        }

        // 그 외 toggle 테스트는 어려움
    }
})
