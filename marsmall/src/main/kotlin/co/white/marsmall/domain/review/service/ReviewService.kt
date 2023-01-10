package co.white.marsmall.domain.review.service

import co.white.marsmall.domain.review.entity.Review
import co.white.marsmall.domain.review.repository.ReviewRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
) {

    fun findById(id: Long): Review =
        reviewRepository.findByIdOrNull(id) ?: throw EntityNotFoundException("No review exists. id: $id")
}
