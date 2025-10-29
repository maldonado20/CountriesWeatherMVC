package sv.edu.udb.countriesweather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sv.edu.udb.countriesweather.BuildConfig
import sv.edu.udb.countriesweather.R
import sv.edu.udb.countriesweather.controllers.CountryDetailController
import sv.edu.udb.countriesweather.models.Country
import sv.edu.udb.countriesweather.utils.Formatters

class CountryDetailFragment: Fragment() {

    private lateinit var country: Country
    private val controller = CountryDetailController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        country = requireArguments().getParcelable("country")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_country_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivFlagBig: ImageView = view.findViewById(R.id.ivFlagBig)
        val tvCountryName: TextView = view.findViewById(R.id.tvCountryName)
        val tvIso: TextView = view.findViewById(R.id.tvIso)
        val tvRegion: TextView = view.findViewById(R.id.tvRegion)
        val tvPopulation: TextView = view.findViewById(R.id.tvPopulation)
        val tvLanguages: TextView = view.findViewById(R.id.tvLanguages)
        val tvCurrencies: TextView = view.findViewById(R.id.tvCurrencies)

        val ivWeatherIcon: ImageView = view.findViewById(R.id.ivWeatherIcon)
        val pbWeather: ProgressBar = view.findViewById(R.id.pbWeather)
        val tvWeatherTitle: TextView = view.findViewById(R.id.tvWeatherTitle)
        val tvWeatherDesc: TextView = view.findViewById(R.id.tvWeatherDesc)
        val tvWeatherError: TextView = view.findViewById(R.id.tvWeatherError)

        ivFlagBig.load(country.flags?.png ?: country.flags?.svg)
        tvCountryName.text = country.name.common ?: "N/D"
        tvIso.text = getString(R.string.iso_codes) + ": " + listOfNotNull(country.cca2, country.cca3).joinToString(" / ")
        tvRegion.text = getString(R.string.region) + ": " + listOfNotNull(country.region, country.subregion).joinToString(" / ")
        tvPopulation.text = getString(R.string.population) + ": " + Formatters.population(country.population)
        tvLanguages.text = getString(R.string.languages) + ": " + Formatters.joinMapValues(country.languages)
        // currencies map is nested; show code: name symbol
        tvCurrencies.text = getString(R.string.currencies) + ": " + (country.currencies?.entries?.joinToString(", ") {
            val info = it.value
            val name = (info as? Map<*, *>)?.get("name") as? String ?: (info.toString())
            val symbol = (info as? Map<*, *>)?.get("symbol") as? String ?: ""
            "${it.key}: ${name} ${symbol}"
        } ?: "N/D")

        val capital = country.capital?.firstOrNull()
        if (capital.isNullOrBlank()) {
            pbWeather.visibility = View.GONE
            tvWeatherError.visibility = View.VISIBLE
            tvWeatherError.text = "Capital no disponible para consultar clima."
            return
        }

        tvWeatherTitle.text = getString(R.string.weather_from, capital)
        tvWeatherDesc.text = getString(R.string.loading)
        pbWeather.visibility = View.VISIBLE
        tvWeatherError.visibility = View.GONE

        viewLifecycleOwner.lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) { controller.loadWeather(BuildConfig.WEATHER_API_KEY, capital) }
            pbWeather.visibility = View.GONE
            result.onSuccess { w ->
                val curr = w.current
                if (curr == null) {
                    tvWeatherError.visibility = View.VISIBLE
                    tvWeatherError.text = "Respuesta sin datos de clima."
                    return@onSuccess
                }
                val cond = curr.condition
                val icon = cond?.icon?.let { if (it.startsWith("//")) "https:$it" else it }
                ivWeatherIcon.load(icon)
                val text = "${cond?.text ?: ""} | ${curr.tempC ?: "?"}°C (${curr.feelsLikeC ?: "?"}°C) | ${getString(R.string.humidity)}: ${curr.humidity ?: "?"}% | ${getString(R.string.wind)}: ${curr.windKph ?: "?"} kph"
                tvWeatherDesc.text = text
            }.onFailure { e ->
                tvWeatherError.visibility = View.VISIBLE
                val msg = when {
                    BuildConfig.WEATHER_API_KEY.isNullOrEmpty() -> "API Key no configurada (WeatherAPI)."
                    e.message?.contains("401") == true -> "Token inválido o expirado (401)."
                    e.message?.contains("404") == true -> "Ciudad no encontrada (404)."
                    else -> "Error: ${e.localizedMessage ?: "desconocido"}"
                }
                tvWeatherError.text = msg
            }
        }
    }
}