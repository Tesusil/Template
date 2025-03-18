import com.tesusil.template.domain.Mapper
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    @Test
    fun `mapper should correctly map to domain model`() {
        // Given
        val mapper = createTestMapper()
        val dataModel = "test-data"

        // When
        val result = mapper.mapToDomainModel(dataModel)

        // Then
        assertEquals(dataModel.length, result)
    }

    @Test
    fun `mapper should correctly map to data model`() {
        // Given
        val mapper = createTestMapper()
        val domainModel = 10

        // When
        val result = mapper.mapToDataModel(domainModel)

        // Then
        assertEquals("a".repeat(domainModel), result)
    }

    @Test
    fun `mapper should correctly map list to domain models`() {
        // Given
        val mapper = createTestMapper()
        val dataModelList = listOf("a", "bb", "ccc")

        // When
        val result = mapper.mapToDomainModelList(dataModelList)

        // Then
        assertEquals(listOf(1, 2, 3), result)
    }

    @Test
    fun `mapper should correctly map list to data models`() {
        // Given
        val mapper = createTestMapper()
        val domainModelList = listOf(1, 2, 3)

        // When
        val result = mapper.mapToDataModelList(domainModelList)

        // Then
        assertEquals(listOf("a", "aa", "aaa"), result)
    }

    private fun createTestMapper(): Mapper<String, Int> {
        return object : Mapper<String, Int> {
            override fun mapToDomainModel(dataModel: String): Int {
                return dataModel.length
            }

            override fun mapToDataModel(domainModel: Int): String {
                return "a".repeat(domainModel)
            }

            override fun mapToDomainModelList(dataModelList: List<String>): List<Int> {
                return dataModelList.map { it.length }
            }

            override fun mapToDataModelList(domainModelList: List<Int>): List<String> {
                return domainModelList.map { "a".repeat(it) }
            }
        }
    }
} 