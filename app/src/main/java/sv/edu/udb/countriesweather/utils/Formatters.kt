package sv.edu.udb.countriesweather.utils

import java.text.NumberFormat
import java.util.Locale

object Formatters {
    fun population(n: Long?): String {
        val v = n ?: return "N/D"
        return NumberFormat.getInstance(Locale.US).format(v)
    }

    fun joinMapValues(map: Map<String, String>?): String {
        if (map == null) return "N/D"
        return map.values.joinToString(", ")
    }

    data class CurrencyInfo(val code: String, val name: String, val symbol: String)
    fun currencies(map: Map<String, Any?>?): String {
        if (map == null) return "N/D"
        val list = map.entries.map { (code, value) ->
            val n = (value as? Map<*, *>)?.get("name") as? String ?: "N/D"
            val s = (value as? Map<*, *>)?.get("symbol") as? String ?: ""
            "$code: $n $s"
        }
        return list.joinToString(", ")
    }
}