package co.white.marsmall.domain.product.service

import co.white.marsmall.domain.product.entity.Product
import co.white.marsmall.domain.product.repository.ProductRepository
import co.white.marsmall.dto.ProductResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {

    fun getById(id: Long): ProductResponse {
        return ProductResponse(findById(id))
    }

    fun create(p: Product): ProductResponse {
        return ProductResponse(productRepository.save(p))
    }

    @Transactional
    fun modify(id: Long, p: Product): ProductResponse {
        return ProductResponse(findById(id).modify(p))
    }

    @Transactional
    fun delete(id: Long) {
        findById(id).delete()
    }

    private fun findById(id: Long): Product =
        productRepository.findByIdAndDeletedFalse(id) ?: throw EntityNotFoundException("No product exists. id: $id")
}
