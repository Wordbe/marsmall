package co.white.marsmall.controller

import co.white.marsmall.domain.product.productRequest
import co.white.marsmall.domain.product.productResponse
import co.white.marsmall.domain.product.service.ProductService
import co.white.marsmall.support.RestControllerTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
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

    @Test
    fun create() {
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
    fun getById() {
        val response = productResponse(id = 1)
        every { productService.getById(1) } returns response

        mockMvc.get("/product/1")
            .andExpect {
                status { isOk() }
                content { jsonResponse(response) }
            }
    }


    @Test
    fun modify() {
        val request = productRequest(name = "jelly 2", price = 2800)
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
    fun delete() {
        every { productService.delete(1) } just runs

        mockMvc.delete("/product/1")
            .andExpect {
                status { isOk() }
            }
    }
}
