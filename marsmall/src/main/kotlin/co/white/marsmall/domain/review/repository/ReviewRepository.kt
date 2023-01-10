package co.white.marsmall.domain.review.repository

import co.white.marsmall.domain.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long>
