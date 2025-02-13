import com.tesusil.template.domain.Result
import com.tesusil.template.domain.map
import org.junit.Assert.*
import org.junit.Test

class ResultTest {

    @Test
    fun `Success result should return correct value`() {
        val result = Result.success(42)
        assertTrue(result is Result.Success)
        assertEquals(42, (result as Result.Success).value)
    }

    @Test
    fun `Failure result should contain exception`() {
        val exception = Exception("Test Exception")
        val result = Result.failure(exception)
        assertTrue(result is Result.Failure)
        assertEquals(exception, (result as Result.Failure).exception)
    }

    @Test
    fun `isSuccessful should return true for Success`() {
        val result = Result.success("Success")
        assertTrue(result.isSuccessful())
    }

    @Test
    fun `isSuccessful should return false for Failure`() {
        val result = Result.failure(Exception("Error"))
        assertFalse(result.isSuccessful())
    }

    @Test
    fun `toUnit should convert Success to Success(Unit)`() {
        val result = Result.success(100).toUnit()
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).value)
    }

    @Test
    fun `toUnit should propagate Failure`() {
        val exception = Exception("Error")
        val result = Result.failure(exception).toUnit()
        assertTrue(result is Result.Failure)
        assertEquals(exception, (result as Result.Failure).exception)
    }

    @Test
    fun `process should invoke onSuccess for Success`() {
        var successValue: Int? = null
        val result = Result.success(5)
        result.process(
            onSuccess = { successValue = it },
            onError = { fail("onError should not be called") }
        )
        assertEquals(5, successValue)
    }

    @Test
    fun `process should invoke onError for Failure`() {
        val exception = Exception("Failure")
        var error: Throwable? = null
        val result = Result.failure(exception)
        result.process(
            onSuccess = { fail("onSuccess should not be called") },
            onError = { error = it }
        )
        assertEquals(exception, error)
    }

    @Test
    fun `getOrNull should return value for Success`() {
        val result = Result.success("Hello")
        assertEquals("Hello", result.getOrNull())
    }

    @Test
    fun `getOrNull should return null for Failure`() {
        val result = Result.failure(Exception("Failure"))
        assertNull(result.getOrNull())
    }

    @Test
    fun `map should transform Success result`() {
        val result = Result.success(10).map { it * 2 }
        assertTrue(result is Result.Success)
        assertEquals(20, (result as Result.Success).value)
    }

    @Test
    fun `map should propagate Failure`() {
        val exception = Exception("Failure")
        val result: Result<Int> = Result.failure(exception).map { 4 }
        assertTrue(result is Result.Failure)
        assertEquals(exception, (result as Result.Failure).exception)
    }
}
