package com.tesusil.datasource.api.exceptions

sealed class ApiExceptions(message: String, code: Int) : Exception(message) {

    companion object {
        const val UNKNOWN_ERROR_CODE = -99
    }

    class NoBodyResponseException(val errorCode: Int) : ApiExceptions(
        message = "Api call response don't contain any data in body!",
        code = errorCode
    )

    class UnSuccessfulRequest(val errorCode: Int) : ApiExceptions(
        message = "Api call wasn't successful. Error code: $errorCode",
        code = errorCode
    )
}


