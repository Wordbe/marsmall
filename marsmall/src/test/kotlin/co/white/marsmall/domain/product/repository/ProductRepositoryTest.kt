package co.white.marsmall.domain.product.repository

import co.white.marsmall.domain.product.product
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.annotation.Rollback

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    lateinit var productRepository: ProductRepository

    @Rollback
    @Test
    fun findById() {
        val product = productRepository.save(product())

        assertThat(productRepository.findByIdOrNull(product.id)).isEqualTo(product)
    }

    @Rollback
    @Test
    fun create() {
        val product = productRepository.save(product())

        assertThat(product.id).isEqualTo(1)
    }

    @Rollback
    @Test
    fun delete() {
        val product = productRepository.save(product())
        productRepository.delete(product)
        assertThat(productRepository.findByIdOrNull(product.id)).isNull()
    }
}
