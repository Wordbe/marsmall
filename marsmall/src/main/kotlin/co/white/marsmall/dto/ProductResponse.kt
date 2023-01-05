package co.white.marsmall.dto

import co.white.marsmall.domain.product.entity.Product
import java.time.LocalDateTime

class ProductResponse(
    val id: Long?,
    val name: String,
    val price: Int,
    val createdAt: LocalDateTime?,
    val createdBy: String?,
    val modifiedAt: LocalDateTime?,
    val modifiedBy: String?,
) {
    companion object {
        operator fun invoke(product: Product) = ProductResponse(
            id = product.id,
            name = product.name,
            price = product.price,
            createdAt = product.createdAt,
            createdBy = product.createdBy,
            modifiedAt = product.modifiedAt,
            modifiedBy = product.modifiedBy,
        )
    }
}

class ProductRequest(
    private val name: String,
    private val price: Int,
) {
    fun toEntity() = Product(
        name = name,
        price = price,
    )
}
