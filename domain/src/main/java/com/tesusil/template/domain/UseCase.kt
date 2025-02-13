package com.tesusil.template.domain

abstract class UseCase<in Params, ResultType> {
    abstract suspend fun execute(params: Params): Result<ResultType>

    suspend operator fun invoke(
        params: Params,
        onSuccess: (ResultType) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        val result = execute(params)
        result.process(
            onSuccess = onSuccess,
            onError = onError
        )
    }
}
