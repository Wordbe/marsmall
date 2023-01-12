package co.white.marsmall.domain.review.repository.query

import co.white.marsmall.domain.review.entity.QReview.review
import co.white.marsmall.domain.review.entity.Review
import co.white.marsmall.dto.ReviewPageParam
import co.white.marsmall.support.orderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ReviewQueryRepository(
    private val queryFactory: JPAQueryFactory,
) {

    fun findPagesNoOffset(r: ReviewPageParam): List<Review> {
        return queryFactory.selectFrom(review)
            .where(ltLastId(r.lastId))
            .orderBy(r.sort.path.orderSpecifier(r.direction))
            .limit(r.size)
            .fetch()
    }

    private fun ltLastId(lastId: Long?): BooleanExpression? =
        if (lastId == null) null else review.id.lt(lastId)
}
