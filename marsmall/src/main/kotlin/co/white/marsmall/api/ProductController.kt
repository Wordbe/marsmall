package co.white.marsmall.api

import co.white.marsmall.domain.product.service.ProductService
import co.white.marsmall.dto.ProductRequest
import co.white.marsmall.dto.ProductResponse
import org.springframework.web.bind.annotation.*

@RestController
class ProductController(
    private val productService: ProductService,
) {

    @PostMapping("/product")
    fun create(@RequestBody pr: ProductRequest): ProductResponse {
        return productService.create(pr.toEntity())
    }

    @GetMapping("/product/{id}")
    fun getById(@PathVariable id: Long): ProductResponse {
        return productService.getById(id)
    }

    @PutMapping("/product/{id}")
    fun modify(@PathVariable id: Long, @RequestBody pr: ProductRequest): ProductResponse {
        return productService.modify(id, pr.toEntity())
    }

    @DeleteMapping("/product/{id}")
    fun delete(@PathVariable id: Long) {
        productService.delete(id)
    }
}
