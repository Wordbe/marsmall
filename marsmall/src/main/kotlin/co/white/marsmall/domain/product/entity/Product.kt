package co.white.marsmall.domain.product.entity

import co.white.marsmall.domain.common.entity.base.Audit
import jakarta.persistence.Entity

@Entity
data class Product(
    var name: String,
    var price: Int,
    var deleted: Boolean = false,
) : Audit() {

    constructor(id: Long? = null, name: String, price: Int, deleted: Boolean = false) : this(name, price, deleted) {
        this.id = id
    }

    fun modify(p: Product): Product {
        name = p.name
        price = p.price
        return this
    }

    fun delete() {
        deleted = true
    }
}
