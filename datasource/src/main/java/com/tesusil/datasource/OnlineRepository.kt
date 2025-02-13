package com.tesusil.datasource

import com.tesusil.datasource.api.exceptions.ApiExceptions
import com.tesusil.template.domain.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

interface OnlineRepository {
}

internal suspend fun <T> OnlineRepository.fetch(
    call: Call<T>,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
): Result<T> {
    return withContext(dispatcher) {
        return@withContext try {
            val callResult = call.execute()
            val code = callResult.code()
            val body = callResult.body()

            if (!callResult.isSuccessful) {
                return@withContext Result.failure(ApiExceptions.UnSuccessfulRequest(code))
            }
            if(body == null) {
                return@withContext Result.failure(ApiExceptions.NoBodyResponseException(code))
            }

            Result.success(body)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}

