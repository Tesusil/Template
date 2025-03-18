package com.tesusil.datasource

import com.tesusil.datasource.api.exceptions.ApiExceptions
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Call
import retrofit2.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

/**
 * @see OnlineRepository
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class OnlineRepositoryTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var testDispatcher: CoroutineDispatcher

    private lateinit var onlineRepository: OnlineRepository
    private lateinit var call: Call<String>

    @Before
    fun setup() {
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher as StandardTestDispatcher)
        
        // Initialize repository
        onlineRepository = object : OnlineRepository {}
        
        // Initialize Call mock
        call = mockk()
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
        val result = onlineRepository.fetch(call, testDispatcher)

        //THEN:
        verify(exactly = 1) { call.execute() }
        assert(result is com.tesusil.template.domain.Result.Failure)
        assertFalse(result.isSuccessful())
        assert((result as com.tesusil.template.domain.Result.Failure).exception is ApiExceptions.UnSuccessfulRequest)
        assert((result.exception as ApiExceptions.UnSuccessfulRequest).errorCode == errorCode)
    }

    @Test
    fun `when fetch response have empty body should return failure`() = runTest {
        //GIVEN:
        val response = Response.success<String>(null)
        every { call.execute() } returns response

        //WHEN:
        val result = onlineRepository.fetch(call, testDispatcher)

        //THEN:
        verify(exactly = 1) { call.execute() }
        assert(result is com.tesusil.template.domain.Result.Failure)
        assert((result as com.tesusil.template.domain.Result.Failure).exception is ApiExceptions.NoBodyResponseException)
        assertFalse(result.isSuccessful())
    }

    @Test
    fun `when fetch catch exception should return failure`() = runTest {
        //GIVEN:
        val exception = InterruptedException()
        every { call.execute() } throws exception

        //When:
        val result = onlineRepository.fetch(call, testDispatcher)

        //Then:
        verify(exactly = 1) { call.execute() }
        assert(result is com.tesusil.template.domain.Result.Failure)
        assert((result as com.tesusil.template.domain.Result.Failure).exception == exception)
    }

    @Test
    fun `when call is successful fetch should return success with data`() = runTest {
        //GIVEN:
        val data = "success"
        val response = Response.success<String>(data)
        every { call.execute() } returns response

        //WHEN:
        val result = onlineRepository.fetch(call, testDispatcher)

        //THEN:
        verify(exactly = 1) { call.execute() }
        assert(result is com.tesusil.template.domain.Result.Success)
        assert((result as com.tesusil.template.domain.Result.Success).value == data)
    }
}