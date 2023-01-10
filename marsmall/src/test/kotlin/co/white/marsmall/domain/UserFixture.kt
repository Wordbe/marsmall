package co.white.marsmall.domain

import co.white.marsmall.domain.user.entity.User

fun user(
    id: Long? = null,
    name: String = "jin",
    email: String = "jin123@email.com",
    password: String = "xvf2xq",
    phone: String? = "01011112222"
) = User(id, name, email, password, phone)
