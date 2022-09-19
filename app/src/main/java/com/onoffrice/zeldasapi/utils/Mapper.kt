package com.onoffrice.zeldasapi.utils

interface Mapper<S, T> {
    fun map(source: S): T
}