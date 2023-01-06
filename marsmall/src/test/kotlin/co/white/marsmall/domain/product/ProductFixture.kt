package co.white.marsmall.domain.product

import co.white.marsmall.domain.product.entity.Product

fun product(id: Long?, name: String) = Product(id = id, name = name, 1800)
fun product(id: Long?) = Product(id = id, name = "jelly", price = 1800)
fun product() = Product(name = "jelly", price = 1800)
