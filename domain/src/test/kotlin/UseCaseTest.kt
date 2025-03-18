import com.tesusil.template.domain.Result
import com.tesusil.template.domain.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class)
class UseCaseTest {

    @Test
    fun `useCase should process successful result`() = runTest {
        // Given
        val testUseCase = object : UseCase<String, Int>() {
            override suspend fun execute(params: String): Result<Int> {
                return Result.success(params.length)
            }
        }
        var result: Int? = null
        var error: Throwable? = null

        // When
        testUseCase.invoke(
            params = "test",
            onSuccess = { result = it },
            onError = { error = it }
        )

        // Then
        assertEquals(4, result)
        assertEquals(null, error)
    }

    @Test
    fun `useCase should process failure result`() = runTest {
        // Given
        val testException = Exception("Test exception")
        val testUseCase = object : UseCase<String, Int>() {
            override suspend fun execute(params: String): Result<Int> {
                return Result.failure(testException)
            }
        }
        var result: Int? = null
        var error: Throwable? = null

        // When
        testUseCase.invoke(
            params = "test",
            onSuccess = { result = it },
            onError = { error = it }
        )

        // Then
        assertEquals(null, result)
        assertEquals(testException, error)
    }

    @Test
    fun `useCase with default handlers should not throw exception`() = runTest {
        // Given
        val testException = Exception("Test exception")
        val testUseCase = object : UseCase<String, Int>() {
            override suspend fun execute(params: String): Result<Int> {
                return Result.failure(testException)
            }
        }

        // When & Then - no exception should be thrown
        testUseCase.invoke(params = "test")
    }
} 