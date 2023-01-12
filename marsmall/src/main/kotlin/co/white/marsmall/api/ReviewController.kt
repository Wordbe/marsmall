package co.white.marsmall.api

import co.white.marsmall.domain.review.service.ReviewService
import co.white.marsmall.dto.ReviewPageParam
import co.white.marsmall.dto.ReviewRequest
import co.white.marsmall.dto.ReviewResponse
import org.springframework.web.bind.annotation.*

@RestController
class ReviewController(
    private val reviewService: ReviewService,
) {

    @PostMapping("/review")
    fun create(@RequestBody reviewRequest: ReviewRequest): ReviewResponse {
        return reviewService.create(reviewRequest.toEntity())
    }

    @GetMapping("/review/{id}")
    fun get(@PathVariable id: Long): ReviewResponse {
        return reviewService.getById(id)
    }

    @GetMapping("/review") // /review?lastId=6&size=5&sort=RATE&direction=DESC
    fun getPages(reviewPageParam: ReviewPageParam): List<ReviewResponse> {
        return reviewService.getPages(reviewPageParam)
    }
}
