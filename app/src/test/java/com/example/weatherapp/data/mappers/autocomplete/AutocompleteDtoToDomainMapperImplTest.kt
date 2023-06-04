package com.example.weatherapp.data.mappers.autocomplete


import com.example.weatherapp.testutils.createAutocompleteData
import com.example.weatherapp.testutils.createAutocompleteResponseDto
import org.junit.Assert
import org.junit.Test

class AutocompleteDtoToDomainMapperImplTest {

    @Test
    fun mapFromAutocompleteDtoTODomainReturnsAutocompleteDomainClass() {
        val mapperImpl = AutocompleteDtoToDomainMapperImpl()
        val expectedAutocompleteData = createAutocompleteData()
        val autocompleteDto = createAutocompleteResponseDto()

        val actualAutocompleteData = mapperImpl.map(autocompleteDto)

        Assert.assertEquals(expectedAutocompleteData, actualAutocompleteData)
    }


}