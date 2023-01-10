package co.white.marsmall.domain

import co.white.marsmall.domain.review.entity.Review

fun review(
    id: Long? = null,
    userId: Long = 1,
    title: String = "title1",
    content: String = "content1"
) = Review(id, userId, title, content)
