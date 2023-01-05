package co.white.marsmall.domain.product.repository

import co.white.marsmall.domain.product.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByIdAndDeletedFalse(id: Long): Product?
}
