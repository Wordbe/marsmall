package co.white.marsmall.domain

import co.white.marsmall.domain.product.entity.Product
import co.white.marsmall.dto.ProductRequest
import co.white.marsmall.dto.ProductResponse
import java.time.LocalDateTime

const val PRODUCT_NAME = "jelly"
const val PRODUCT_PRICE = 1800
val PRODUCT_AUDITED_AT: LocalDateTime = LocalDateTime.of(2023,1,7,12,0,0)
const val PRODUCT_AUDITOR = "marsmall"

fun product(
    id: Long? = null,
    name: String = PRODUCT_NAME,
    price: Int = PRODUCT_PRICE,
    deleted: Boolean = false
) = Product(id, name, price, deleted)

fun productRequest(
    name: String = PRODUCT_NAME,
    price: Int = PRODUCT_PRICE,
) = ProductRequest(name, price)

fun productResponse(
    id: Long? = null,
    name: String = PRODUCT_NAME,
    price: Int = PRODUCT_PRICE,
    createdAt: LocalDateTime = PRODUCT_AUDITED_AT,
    createdBy: String = PRODUCT_AUDITOR,
    modifiedAt: LocalDateTime = PRODUCT_AUDITED_AT,
    modifiedBy: String = PRODUCT_AUDITOR,
) = ProductResponse(id, name, price, createdAt, createdBy, modifiedAt,modifiedBy)
