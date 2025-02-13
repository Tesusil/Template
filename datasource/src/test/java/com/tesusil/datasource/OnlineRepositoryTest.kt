package com.tesusil.datasource

import com.tesusil.datasource.api.endpoints.UserEndpoints
import com.tesusil.datasource.api.exceptions.ApiExceptions
import com.tesusil.datasource.api.models.UserApiModel
import com.tesusil.template.domain.Result
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Response


/**
 * @see OnlineRepository
 */
@OptIn(ExperimentalCoroutinesApi::class)
class OnlineRepositoryTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK(relaxed = true)
    lateinit var call: Call<String>

    val onlineRepository = object : OnlineRepository {}

    private val dispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetch response with error should return failure`() = runTest {
        //GIVEN:
        val errorCode = 404
        val errorResponseBody = "Not found".toResponseBody()
        val errorResponse = Response.error<String>(errorCode, errorResponseBody)
        every { call.execute() } returns errorResponse

        //WHEN:
        val result = onlineRepository.fetch(call, dispatcher)

        //THEN:
        verify(exactly = 1) { call.execute() }
        assert(result is Result.Failure)
        assertFalse(result.isSuccessful())
        assert((result as Result.Failure).exception is ApiExceptions.UnSuccessfulRequest)
        assert((result.exception as ApiExceptions.UnSuccessfulRequest).errorCode == errorCode)
    }

    @Test
    fun `when fetch response have empty body should return failure`() = runTest {
        //GIVEN:
        val response = Response.success<String>(null)
        every { call.execute() } returns response

        //WHEN:
        val result = onlineRepository.fetch(call, dispatcher)

        //THEN:
        verify(exactly = 1) { call.execute() }
        assert(result is Result.Failure)
        assert((result as Result.Failure).exception is ApiExceptions.NoBodyResponseException)
        assertFalse(result.isSuccessful())
    }

    @Test
    fun `when fetch catch exception should return failure`() = runTest {
        //GIVEN:
        val exception = InterruptedException()
        every { call.execute() } throws exception

        //When:
        val result = onlineRepository.fetch(call, dispatcher)

        //Then:
        verify(exactly = 1) { call.execute() }
        assert(result is Result.Failure)
        assert((result as Result.Failure).exception == exception)
    }

    @Test
    fun `when call is successful fetch should return success with data`() = runTest {
        //GIVEN:
        val data = "success"
        val response = Response.success<String>(data)
        every { call.execute() } returns response

        //WHEN:
        val result = onlineRepository.fetch(call, dispatcher)

        //THEN:
        verify(exactly = 1) { call.execute() }
        assert(result is Result.Success)
        assert((result as Result.Success).value == data)
    }
}