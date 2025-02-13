package com.tesusil.template.domain

import java.io.Serializable

sealed class Result<out ResultType>: Serializable {
    data class Success<ResultType>(val value: ResultType) : Result<ResultType>()
    data class Failure(val exception: Exception) : Result<Nothing>()

    fun isSuccessful(): Boolean = this is Success

    fun toUnit(): Result<Unit> {
        return when(this) {
            is Failure -> failure(exception)
            is Success -> success(Unit)
        }
    }

    fun process(
        onSuccess: (ResultType) -> Unit,
        onError: (Throwable) -> Unit,
    ): Result<ResultType> {
        when (this) {
            is Failure -> onError(exception)
            is Success -> onSuccess(value)
        }
        return this
    }

    fun getOrNull(): ResultType? {
        return when(this) {
            is Failure -> null
            is Success -> value
        }
    }

    companion object {
        fun <ResultType> success(value: ResultType): Result<ResultType> = Success(value)

        fun failure(exception: Exception): Result<Nothing> = Failure(exception)
    }
}

fun<Type, MappedType> Result<Type>.map(transform: (Type) -> MappedType): Result<MappedType> {
    return when(this) {
        is Result.Failure -> Result.failure(this.exception)
        is Result.Success -> Result.success(transform(value))
    }
}