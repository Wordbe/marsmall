package co.white.marsmall.api

import co.white.marsmall.api.common.ErrorResponse
import co.white.marsmall.domain.product.service.ProductService
import co.white.marsmall.domain.productRequest
import co.white.marsmall.domain.productResponse
import co.white.marsmall.support.RestControllerTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put


@WebMvcTest(ProductController::class)
class ProductControllerTest : RestControllerTest() {
    @MockkBean
    lateinit var productService: ProductService

    @Nested
    inner class Create {
        @Test
        fun success() {
            val request = productRequest()
            val response = productResponse(id = 1)
            every { productService.create(request.toEntity()) } returns response

            mockMvc.post("/product") {
                jsonRequest(request)
            }.andExpect {
                status { isOk() }
                content { jsonResponse(response) }
            }
        }

        @Test
        fun `When no request body, then bad request`() {
            mockMvc.post("/product")
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }

    @Nested
    inner class GetById {
        @Test
        fun success() {
            val response = productResponse(id = 1)
            every { productService.getById(1) } returns response

            mockMvc.get("/product/1")
                .andExpect {
                    status { isOk() }
                    content { jsonResponse(response) }
                }
        }

        @Test
        fun `When exception, then error response`() {
            every { productService.getById(1) } throws EntityNotFoundException("No product exists. id: 1")

            mockMvc.get("/product/1")
                .andExpect {
                    status { isNotFound() }
                    content { jsonResponse(ErrorResponse("No product exists. id: 1")) }
                }
        }
    }

    @Nested
    inner class Modify {
        private val request = productRequest(name = "jelly 2", price = 2800)

        @Test
        fun success() {
            val response = productResponse(id = 1, name = "jelly 2", price = 2800)
            every { productService.modify(1, request.toEntity()) } returns response

            mockMvc.put("/product/1") {
                jsonRequest(request)
            }.andExpect {
                status { isOk() }
                content { jsonResponse(response) }
            }
        }

        @Test
        fun `When exception, then error response`() {
            every { productService.modify(1, request.toEntity()) } throws EntityNotFoundException("No product exists. id: 1")

            mockMvc.put("/product/1") {
                jsonRequest(request)
            }.andExpect {
                status { isNotFound() }
                content { jsonResponse(ErrorResponse("No product exists. id: 1")) }
            }
        }
    }

    @Nested
    inner class Delete {
        @Test
        fun delete() {
            every { productService.delete(1) } just runs

            mockMvc.delete("/product/1")
                .andExpect {
                    status { isOk() }
                }
        }

        @Test
        fun `When exception, then error response`() {
            every { productService.delete(1) } throws EntityNotFoundException("No product exists. id: 1")

            mockMvc.delete("/product/1")
                .andExpect {
                    status { isNotFound() }
                    content { jsonResponse(ErrorResponse("No product exists. id: 1")) }
                }
        }
    }
}
