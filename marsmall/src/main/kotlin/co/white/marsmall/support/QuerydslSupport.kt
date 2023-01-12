package co.white.marsmall.support

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.NumberPath
import org.springframework.data.domain.Sort

fun NumberPath<*>.orderSpecifier(direction: Sort.Direction): OrderSpecifier<*> {
    return if (direction.isAscending) asc() else desc()
}
