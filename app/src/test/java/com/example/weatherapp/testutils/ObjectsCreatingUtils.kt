package com.example.weatherapp.testutils

import android.location.Address
import com.example.weatherapp.data.databases.location_database.entities.CurrentWeatherEntity
import com.example.weatherapp.data.databases.location_database.entities.LocationEntity
import com.example.weatherapp.data.databases.location_database.entities.WeatherForecastEntity
import com.example.weatherapp.data.remote.autocomplete.dto.AutocompleteResponseDto
import com.example.weatherapp.data.remote.weather.dto.*
import com.example.weatherapp.domain.models.Forecast
import com.example.weatherapp.domain.models.GeocodingResult
import com.example.weatherapp.domain.models.LatLng
import com.example.weatherapp.domain.models.Location
import io.mockk.every
import io.mockk.mockk
import com.example.weatherapp.domain.models.CurrentWeather as DomainCurrentWeather

fun createAutocompleteResponseDto(
    id: String = "",
    type: String = "",
    code: String = "",
    name: String = "",
    countryCode: String = "",
    countryName: String = "",
    stateCode: String? = null,
    coordinates: Coord = Coord(0.0f, 0.0f),
    indexString: List<String> = listOf(),
    weight: Long = 0,
    cases: Map<String, String>? = null,
    countryCases: Map<String, String>? = null,
    mainAirportName: String? = null,
) = AutocompleteResponseDto(
    id,
    type,
    code,
    name,
    countryCode,
    countryName,
    stateCode,
    coordinates,
    indexString,
    weight,
    cases,
    countryCases,
    mainAirportName
)

fun createAutocompleteData(textLine: String = "") = textLine

fun createCurrentWeatherDto(
    coord: Coord = Coord(0.0f, 0.0f),
    weather: List<CurrentWeather> = listOf(CurrentWeather(0, "", "", "")),
    base: String = "",
    main: CurrentMain = CurrentMain(0.0, 0.0, 0.0, 0.0, 0, 0),
    visibility: Int = 0,
    wind: Wind = Wind(0.0, 0, 0.0),
    clouds: Clouds = Clouds(0),
    rain: CurrentRain? = null,
    snow: CurrentSnow? = null,
    dataTimeUtc: Long = 0,
    sys: CurrentSys = CurrentSys(0, 0, "", "", 0, 0),
    timezone: Int = 0,
    id: Int = 0,
    name: String = "",
    cod: Int = 0,
) = CurrentWeatherResponseDto(
    coord,
    weather,
    base,
    main,
    visibility,
    wind,
    clouds,
    rain,
    snow,
    dataTimeUtc,
    sys,
    timezone,
    id,
    name,
    cod
)

fun createCurrentWeatherEntity(
    locationId: Long = 0,
    weatherConditionId: Int = 0,
    weatherName: String = "",
    weatherDescription: String = "",
    weatherIconName: String = "",
    temperature: Float = 0.0f,
    pressure: Int = 0,
    humidity: Int = 0,
    windSpeed: Float = 0.0f,
    windDegree: Int = 0,
    dateTimeUnixUtc: Long = 0,
    shiftFromUtcSec: Long = 0,
) = CurrentWeatherEntity(
    locationId,
    weatherConditionId,
    weatherName,
    weatherDescription,
    weatherIconName,
    temperature,
    pressure,
    humidity,
    windSpeed,
    windDegree,
    dateTimeUnixUtc,
    shiftFromUtcSec
)

fun createCurrentWeather(
    weatherConditionId: Int = 0,
    weatherName: String = "",
    weatherDescription: String = "",
    weatherIconName: String = "",
    temperature: Int = 0,
    pressure: Int = 0,
    humidity: Int = 0,
    windSpeed: Float = 0.0f,
    windDirectionDegrees: Int = 0,
    dateTimeUnixUtc: Long = 0,
    shiftFromUtcSeconds: Long = 0,
) = DomainCurrentWeather(
    weatherConditionId,
    weatherName,
    weatherDescription,
    weatherIconName,
    temperature,
    pressure,
    humidity,
    windSpeed,
    windDirectionDegrees,
    dateTimeUnixUtc,
    shiftFromUtcSeconds
)

fun createWeatherForecastDto(
    cod: String = "",
    message: Int = 0,
    timestampsCount: Int = 0,
    forecastWeatherList: List<ForecastWeather> = List(
        timestampsCount
    ) {
        createForecastWeatherForDto()
    },
    city: City = City(0, "", Coord(0.0f, 0.0f), "", 0, 0, 0, 0),
) = WeatherForecastResponseDto(cod, message, timestampsCount, forecastWeatherList, city)

fun createForecastWeatherForDto(
    dt: Long = 0,
    main: ForecastMain = ForecastMain(0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0, 0.0),
    weather: List<WeatherDescription> = listOf(WeatherDescription(0, "", "", "")),
    clouds: Clouds = Clouds(0),
    wind: Wind = Wind(0.0, 0, 0.0),
    forecastRain: ForecastRain? = null,
    forecastSnow: ForecastSnow? = null,
    visibility: Int = 0,
    pop: Double = 0.0,
    sys: ForecastSys = ForecastSys(""),
    dataTimeText: String = "",
) = ForecastWeather(
    dt,
    main,
    weather,
    clouds,
    wind,
    forecastRain,
    forecastSnow,
    visibility,
    pop,
    sys,
    dataTimeText
)

fun createForecastEntity(
    locationId: Long = 0,
    weatherName: String = "",
    temperature: Float = 0.0f,
    dateTimeUnixUtc: Long = 0,
    shiftFromUtcSeconds: Long = 0,
) = WeatherForecastEntity(
    locationId,
    weatherName,
    temperature,
    dateTimeUnixUtc,
    shiftFromUtcSeconds
)

fun createForecastDomain(
    weatherIconName: String = "",
    temperature: Int = 0,
    dateTimeUnixUtc: Long = 0,
    shiftFromUtcSeconds: Long = 0,
) = Forecast(weatherIconName, temperature, dateTimeUnixUtc, shiftFromUtcSeconds)

fun createGeocodingResult(
    latLng: LatLng = LatLng(0.0f, 0.0f),
    locationName: String = "",
    countryName: String = ""
) = GeocodingResult(latLng, locationName, countryName)

fun createAddress(
    latitude: Double = 0.0,
    longitude: Double = 0.0,
    featureName: String = "",
    countryName: String = ""
): Address {
    val addressMock = mockk<Address>()
    every { addressMock.latitude } returns latitude
    every { addressMock.longitude } returns longitude
    every { addressMock.featureName } returns featureName
    every { addressMock.countryName } returns countryName
    return addressMock
}

fun createLocationDomain(
    id: Long = 0,
    name: String = "",
    isFavorite: Boolean = false,
    hasNextDayForecast: Boolean = false,
    latLng: LatLng = LatLng(0.0f, 0.0f),
) = Location(id, name, isFavorite, hasNextDayForecast, latLng)

fun createLocationEntity(
    id: Long = 0,
    name: String = "",
    isFavorite: Boolean = false,
    hasNextDayForecast: Boolean = false,
    latLng: LatLng = LatLng(0.0f, 0.0f),
) = LocationEntity(id, name, latLng.latitude, latLng.longitude, isFavorite, hasNextDayForecast)