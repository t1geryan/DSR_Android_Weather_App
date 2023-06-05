package com.example.weatherapp.data.repositories

import com.example.weatherapp.data.mappers.autocomplete.AutocompleteDtoToDomainMapper
import com.example.weatherapp.data.remote.autocomplete.api.AutocompleteApi
import com.example.weatherapp.domain.AppException
import com.example.weatherapp.domain.BackendException
import com.example.weatherapp.domain.ConnectionException
import com.example.weatherapp.testutils.asserted
import com.example.weatherapp.testutils.createAutocompleteResponseDto
import com.example.weatherapp.utils.locale.CurrentLocaleProvider
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class LocationsAutocompleteDataProviderRepositoryImplTest {

    @get:Rule
    val rule = MockKRule(this)

    @RelaxedMockK
    lateinit var autocompleteApi: AutocompleteApi

    @MockK
    lateinit var currentLocaleProvider: CurrentLocaleProvider

    @MockK
    lateinit var autocompleteDtoToDomainMapper: AutocompleteDtoToDomainMapper

    private lateinit var repositoryImpl: LocationsAutocompleteDataProviderRepositoryImpl

    @Before
    fun setUp() {
        repositoryImpl = LocationsAutocompleteDataProviderRepositoryImpl(
            autocompleteApi,
            currentLocaleProvider,
            autocompleteDtoToDomainMapper
        )
        every { currentLocaleProvider.provideIso3166Code() } returns LOCALE
        every { autocompleteDtoToDomainMapper.map(AUTOCOMPLETE_DTO_1) } returns AUTOCOMPLETE_DATA_1
    }

    @Test
    fun getAutocompleteDataOnApiSuccessReturnsAutocompleteData() = runTest {
        coEvery { autocompleteApi.getAutocompleteData(INPUT, any()) } returns Response.success(
            listOf(AUTOCOMPLETE_DTO_1)
        )

        val autocompleteData = repositoryImpl.getAutocompleteData(INPUT)

        Assert.assertArrayEquals(
            listOf(AUTOCOMPLETE_DATA_1).toTypedArray(),
            autocompleteData.toTypedArray()
        )
        coVerify(exactly = 1) {
            autocompleteApi.getAutocompleteData(INPUT, LOCALE)
        }
        confirmVerified(autocompleteApi)
    }

    @Test
    fun getAutocompleteDataWithBadInputReturnsEmptyList() = runTest {
        coEvery { autocompleteApi.getAutocompleteData(BAD_INPUT, any()) } returns Response.success(
            emptyList()
        )

        val autocompleteData = repositoryImpl.getAutocompleteData(BAD_INPUT)

        Assert.assertTrue(autocompleteData.isEmpty())
        coVerify(exactly = 1) {
            autocompleteApi.getAutocompleteData(BAD_INPUT, LOCALE)
        }
        confirmVerified(autocompleteApi)
    }

    @Test(expected = BackendException::class)
    fun getAutocompleteDataOnErrorResponseBackendException() = runTest {
        coEvery { autocompleteApi.getAutocompleteData(INPUT, any()) } returns Response.error(
            401,
            mockk(relaxed = true)
        )

        repositoryImpl.getAutocompleteData(INPUT)

        asserted()
    }

    @Test(expected = BackendException::class)
    fun getAutocompleteDataOnApiErrorThrowsBackendException() = runTest {
        coEvery {
            autocompleteApi.getAutocompleteData(
                INPUT,
                any()
            )
        } throws HttpException(Response.error<Int>(405, mockk(relaxed = true)))

        repositoryImpl.getAutocompleteData(INPUT)

        asserted()
    }

    @Test(expected = ConnectionException::class)
    fun getAutocompleteDataWithoutNetworkConnectionThrowsConnectionException() = runTest {
        coEvery { autocompleteApi.getAutocompleteData(INPUT, any()) } throws IOException()

        repositoryImpl.getAutocompleteData(INPUT)

        asserted()
    }

    @Test(expected = AppException::class)
    fun getAutocompleteDataOnApiExceptionThrowsAppException() = runTest {
        coEvery { autocompleteApi.getAutocompleteData(INPUT, any()) } throws RuntimeException()

        repositoryImpl.getAutocompleteData(INPUT)

        asserted()
    }

    companion object {
        private const val LOCALE = "en"
        private const val INPUT = "Mos"
        private const val BAD_INPUT = "NO CITY WITH THIS NAME"

        private const val AUTOCOMPLETE_DATA_1 = "Moscow, Russia"
        private val AUTOCOMPLETE_DTO_1 =
            createAutocompleteResponseDto(name = "Moscow", countryName = "Russia")
    }
}