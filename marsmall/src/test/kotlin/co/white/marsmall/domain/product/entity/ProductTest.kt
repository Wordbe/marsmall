package co.white.marsmall.domain.product.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProductTest {

    @Test
    fun modify() {
        val new = Product(name = "jelly 2", price = 2800)
        val origin = Product(id = 1L, name = "jelly", price = 1800)

        val result = origin.modify(new)

        assertThat(result).isEqualTo(Product(id = 1L, name = "jelly 2", price = 2800))
    }

    @Test
    fun delete() {
        val product = Product(name = "jelly", price = 1800)

        product.delete()

        assertThat(product).isEqualTo(Product(name = "jelly", price = 1800, deleted = true))
    }
}
