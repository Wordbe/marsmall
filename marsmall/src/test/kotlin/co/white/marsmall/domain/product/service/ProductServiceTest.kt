package co.white.marsmall.domain.product.service

import co.white.marsmall.domain.product.product
import co.white.marsmall.domain.product.repository.ProductRepository
import co.white.marsmall.dto.ProductResponse
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ProductServiceTest : StringSpec({
    val productRepository = mockk<ProductRepository>()
    val productService = ProductService(productRepository)

    "getById" {
        every { productRepository.findByIdAndDeletedFalse(1) } returns product(1)

        val result = productService.getById(1)

        result shouldBe ProductResponse(product(1))
    }

    "create" {
        val product = product(1)
        every { productRepository.save(product) } returns product

        val result = productService.create(product)

        result shouldBe ProductResponse(product)
    }

    "modify" {
        every { productRepository.findByIdAndDeletedFalse(1) } returns product(1)
        val requested = product(1, "jelly 2")

        val result = withContext(Dispatchers.IO) { // Solve for "Possibly blocking call in non-blocking context could lead to thread starvation"
            productService.modify(1, requested)
        }

        result shouldBe ProductResponse(requested)
    }

    "delete" {
        val product = product(1)
        every { productRepository.findByIdAndDeletedFalse(1) } returns product

        withContext(Dispatchers.IO) { // Solve for "Possibly blocking call in non-blocking context could lead to thread starvation"
            productService.delete(1)
        }

        product.deleted shouldBe true
    }
})
