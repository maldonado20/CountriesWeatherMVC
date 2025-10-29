package sv.edu.udb.countriesweather.network

import retrofit2.http.GET
import retrofit2.http.Query
import sv.edu.udb.countriesweather.models.WeatherResponse

interface WeatherApiService {
    @GET("current.json")
    suspend fun current(
        @Query("key") key: String,
        @Query("q") city: String
    ): WeatherResponse
}