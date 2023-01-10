package co.white.marsmall.api

import co.white.marsmall.domain.review.service.ReviewReactionService
import co.white.marsmall.dto.ReviewReactionCountRequest
import co.white.marsmall.dto.ReviewReactionCountResponse
import co.white.marsmall.dto.ReviewReactionRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReviewReactionController(
    val reviewReactionService: ReviewReactionService
) {

    @GetMapping("/review/reaction")
    fun countByReviewIdAndType(r: ReviewReactionCountRequest): ReviewReactionCountResponse {
        return reviewReactionService.countByReviewIdAndType(r.reviewId, r.type)
    }

    @PostMapping("/review/reaction/toggle")
    fun toggle(@RequestBody r: ReviewReactionRequest) {
        return reviewReactionService.toggle(r)
    }
}
