package co.white.marsmall.domain.review.repository.query

import co.white.marsmall.config.QuerydslConfig
import co.white.marsmall.domain.review
import co.white.marsmall.domain.review.repository.ReviewRepository
import co.white.marsmall.dto.ReviewPageParam
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(value = [QuerydslConfig::class, ReviewQueryRepository::class])
class ReviewQueryRepositoryTest {
    @Autowired
    lateinit var reviewRepository: ReviewRepository

    @Autowired
    lateinit var reviewQueryRepository: ReviewQueryRepository

    @Test
    fun findPages() {
        for (i in 1..10) {
            reviewRepository.save(review(rate = (i % 5) + 1, content = "good$i"))
        }

        val reviews = reviewQueryRepository.findPagesNoOffset(ReviewPageParam(size = 3))

        reviews shouldHaveSize 3
        reviews[0].id shouldBe 10
        reviews[1].id shouldBe 9
        reviews[2].id shouldBe 8
    }
}
