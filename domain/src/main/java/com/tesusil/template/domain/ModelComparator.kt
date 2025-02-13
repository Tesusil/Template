package com.tesusil.template.domain

interface ModelComparator<T, R> {
    fun compare(data1: T?, data2: R?): Boolean
}