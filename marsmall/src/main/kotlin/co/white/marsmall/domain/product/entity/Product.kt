package co.white.marsmall.domain.product.entity

import co.white.marsmall.domain.common.entity.base.Audit
import jakarta.persistence.Entity

@Entity
class Product(
    id: Long? = null,
    var name: String,
    var price: Int,
    var deleted: Boolean = false,
) : Audit(id = id) {

    fun modify(p: Product): Product {
        name = p.name
        price = p.price
        return this
    }

    fun delete() {
        deleted = true
    }
}
