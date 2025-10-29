package sv.edu.udb.countriesweather.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryName(
    @SerializedName("common") val common: String? = null
) : Parcelable

@Parcelize
data class Flags(
    @SerializedName("png") val png: String? = null,
    @SerializedName("svg") val svg: String? = null
) : Parcelable

@Parcelize
data class CurrencyInfo(
    val name: String? = null,
    val symbol: String? = null
) : Parcelable

@Parcelize
data class Country(
    val name: CountryName = CountryName(),
    val capital: List<String>? = emptyList(),
    val region: String? = null,
    val subregion: String? = null,
    val population: Long? = 0L,
    val flags: Flags? = Flags(),
    val cca2: String? = null,
    val cca3: String? = null,
    val currencies: Map<String, CurrencyInfo>? = emptyMap(),
    val languages: Map<String, String>? = emptyMap(),
    val latlng: List<Double>? = emptyList()
) : Parcelable