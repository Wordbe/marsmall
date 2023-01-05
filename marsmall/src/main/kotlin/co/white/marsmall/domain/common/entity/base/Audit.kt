package co.white.marsmall.domain.common.entity.base

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class Audit (
    id: Long? = null,
    @CreatedDate
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null,
    @CreatedBy
    @Column(updatable = false)
    var createdBy: String? = null,
    @LastModifiedDate
    var modifiedAt: LocalDateTime? = null,
    @LastModifiedBy
    var modifiedBy: String? = null,
) : Id(id) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Audit) return false
        if (!super.equals(other)) return false

        if (createdAt != other.createdAt) return false
        if (createdBy != other.createdBy) return false
        if (modifiedAt != other.modifiedAt) return false
        if (modifiedBy != other.modifiedBy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (createdAt?.hashCode() ?: 0)
        result = 31 * result + (createdBy?.hashCode() ?: 0)
        result = 31 * result + (modifiedAt?.hashCode() ?: 0)
        result = 31 * result + (modifiedBy?.hashCode() ?: 0)
        return result
    }
}

@Component
@EnableJpaAuditing
class CommonAuditorAware : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        return Optional.of("marsmall")
    }

}
