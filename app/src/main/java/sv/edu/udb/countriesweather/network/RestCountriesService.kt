package sv.edu.udb.countriesweather.network

import retrofit2.http.GET
import retrofit2.http.Path
import sv.edu.udb.countriesweather.models.Country

interface RestCountriesService {
    @GET("v3.1/region/{region}")
    suspend fun byRegion(@Path("region") region: String): List<Country>
}