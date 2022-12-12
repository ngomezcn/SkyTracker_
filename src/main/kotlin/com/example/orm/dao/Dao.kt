package com.example.orm.dao

import java.util.*

interface Dao<T> {
    operator fun get(id: Long): Optional<T>?
    val all: List<T>?
    fun save(t: T)
    fun update(t: T, params: Array<String?>?)
    fun delete(t: T)
}