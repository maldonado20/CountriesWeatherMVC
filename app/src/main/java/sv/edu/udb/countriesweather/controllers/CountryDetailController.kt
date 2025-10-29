package sv.edu.udb.countriesweather.controllers

import sv.edu.udb.countriesweather.models.WeatherResponse
import sv.edu.udb.countriesweather.network.ApiClient
import sv.edu.udb.countriesweather.network.WeatherApiService

class CountryDetailController {
    private val service = ApiClient.weatherApi().create(WeatherApiService::class.java)

    suspend fun loadWeather(apiKey: String, city: String): Result<WeatherResponse> {
        return try {
            val resp = service.current(apiKey, city)
            Result.success(resp)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}