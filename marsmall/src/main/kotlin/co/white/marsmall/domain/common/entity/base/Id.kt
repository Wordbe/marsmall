package co.white.marsmall.domain.common.entity.base

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class Id(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is co.white.marsmall.domain.common.entity.base.Id) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
