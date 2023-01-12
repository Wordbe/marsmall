package co.white.marsmall.dto

import co.white.marsmall.domain.review.entity.QReview.review
import co.white.marsmall.domain.review.entity.Review
import com.querydsl.core.types.dsl.NumberPath
import org.springframework.data.domain.Sort.Direction

data class ReviewRequest(
    val userId: Long,
    val rate: Int,
    val content: String
) {

    fun toEntity() = Review(
        userId = userId,
        rate = rate,
        content = content
    )
}

data class ReviewPageParam(
    val lastId: Long? = null,
    val size: Long = 10,
    val sort: ReviewSortType = ReviewSortType.ID,
    val direction: Direction = Direction.DESC,
)

enum class ReviewSortType(val path: NumberPath<*>) {
    ID(review.id),
    RATE(review.rate);
}

data class ReviewResponse(
    val id: Long?,
    val userId: Long,
    val rate: Int,
    val content: String
) {
    companion object {
        operator fun invoke(r: Review) = ReviewResponse(
            id = r.id,
            userId = r.userId,
            rate = r.rate,
            content = r.content
        )
    }
}
