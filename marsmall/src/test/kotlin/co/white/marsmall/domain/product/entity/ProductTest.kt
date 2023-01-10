package co.white.marsmall.domain.product.entity

import co.white.marsmall.domain.product
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProductTest {

    @Test
    fun modify() {
        val new = product(name = "jelly 2", price = 2800)
        val origin = product(id = 1)

        val result = origin.modify(new)

        assertThat(result).isEqualTo(product(id = 1, name = "jelly 2", price = 2800))
    }

    @Test
    fun delete() {
        val product = product()

        product.delete()

        assertThat(product).isEqualTo(product(deleted = true))
    }
}
