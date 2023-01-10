package co.white.marsmall.domain.product.service

import co.white.marsmall.domain.product
import co.white.marsmall.domain.product.repository.ProductRepository
import co.white.marsmall.dto.ProductResponse
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ProductServiceTest : StringSpec({
//    isolationMode = IsolationMode.InstancePerTest // isolated in each test method

    val productRepository = mockk<ProductRepository>()
    val productService = ProductService(productRepository)

    "getById" {
        every { productRepository.findByIdAndDeletedFalse(1) } returns product(id = 1)

        val result = productService.getById(id = 1)

        result shouldBe ProductResponse(product(id = 1))
    }

    "create" {
        val product = product(id = 1)
        every { productRepository.save(product) } returns product

        val result = productService.create(product)

        result shouldBe ProductResponse(product)
    }

    "modify" {
        every { productRepository.findByIdAndDeletedFalse(1) } returns product(id = 1)
        val requested = product(id = 1, name = "jelly 2")

        val result = withContext(Dispatchers.IO) { // Solve for "Possibly blocking call in non-blocking context could lead to thread starvation"
            productService.modify(1, requested)
        }

        verify(exactly = 1) { productRepository.findByIdAndDeletedFalse(1) }
        result shouldBe ProductResponse(requested)
    }

    "delete" {
        val product = product(id = 1)
        every { productRepository.findByIdAndDeletedFalse(1) } returns product

        withContext(Dispatchers.IO) { // Solve for "Possibly blocking call in non-blocking context could lead to thread starvation"
            productService.delete(1)
        }

        verify(exactly = 1) { productRepository.findByIdAndDeletedFalse(1) }
        product.deleted shouldBe true
    }

    // When mock creating costs high, clearMocks better solution
    afterTest {
        clearMocks(productRepository)
    }
})
