package sv.edu.udb.countriesweather.controllers

import sv.edu.udb.countriesweather.models.Country
import sv.edu.udb.countriesweather.network.ApiClient
import sv.edu.udb.countriesweather.network.RestCountriesService

class CountriesController {
    private val service = ApiClient.restCountries().create(RestCountriesService::class.java)

    suspend fun loadByRegion(region: String): Result<List<Country>> {
        return try {
            val data = service.byRegion(region)
            Result.success(data.sortedBy { it.name.common ?: "" })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}