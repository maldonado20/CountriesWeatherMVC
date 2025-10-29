package sv.edu.udb.countriesweather.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location") val location: WeatherLocation? = null,
    @SerializedName("current") val current: WeatherCurrent? = null
)

data class WeatherLocation(
    val name: String? = null,
    val country: String? = null
)

data class WeatherCurrent(
    @SerializedName("temp_c") val tempC: Double? = null,
    @SerializedName("temp_f") val tempF: Double? = null,
    val humidity: Int? = null,
    @SerializedName("wind_kph") val windKph: Double? = null,
    val condition: WeatherCondition? = null,
    @SerializedName("feelslike_c") val feelsLikeC: Double? = null
)

data class WeatherCondition(
    val text: String? = null,
    val icon: String? = null // returns like //cdn.weatherapi.com/weather/64x64/day/113.png (we'll prefix https:)
)