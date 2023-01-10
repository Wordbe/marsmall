package co.white.marsmall.domain

import co.white.marsmall.domain.review.entity.Review

fun review(
    id: Long? = null,
    userId: Long = 1,
    rate: Int = 5,
    content: String = "content1"
) = Review(id, userId, rate, content)
