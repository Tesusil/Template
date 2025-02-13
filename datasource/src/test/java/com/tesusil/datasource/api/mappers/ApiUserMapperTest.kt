package com.tesusil.datasource.api.mappers

import com.tesusil.datasource.api.UserTestData
import com.tesusil.datasource.api.comparators.UserComparator
import io.mockk.junit4.MockKRule
import org.junit.Rule
import org.junit.Test

/**
 * @see ApiUserMapper
 */
class ApiUserMapperTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val comparator = UserComparator()
    private val mapper = ApiUserMapper()

    @Test
    fun `map domain model to api model`() {
        //GIVEN:
        val modelToMap = UserTestData.DOMAIN_USER_MODEL

        //WHEN:
        val result = mapper.mapToDataModel(modelToMap)

        //THEN:
        assert(comparator.compare(modelToMap, result))
    }

    @Test
    fun `map api model to domain model`() {
        //GIVEN:
        val modelToMap = UserTestData.DATA_USER_MODEL

        //WHEN:
        val result = mapper.mapToDomainModel(modelToMap)

        //THEN:
        assert(comparator.compare(result, modelToMap))
    }

    @Test
    fun `map domain model list to data model list`() {
        //GIVEN:
        val listToMap = UserTestData.DOMAIN_USER_LIST

        //WHEN:
        val result = mapper.mapToDataModelList(listToMap)

        //THEN:
        assert(listToMap.size == result.size)
        result.forEachIndexed { index, userApiModel ->
            assert(comparator.compare(listToMap[index], userApiModel))
        }
    }

    @Test
    fun mapToDataModelList() {
        //GIVEN:
        val listToMap = UserTestData.API_USER_LIST

        //WHEN:
        val result = mapper.mapToDomainModelList(listToMap)

        //THEN:
        assert(listToMap.size == result.size)
        result.forEachIndexed { index, userApiModel ->
            assert(comparator.compare(userApiModel, listToMap[index]))
        }
    }
}

