package sv.edu.udb.countriesweather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sv.edu.udb.countriesweather.R
import sv.edu.udb.countriesweather.controllers.CountriesController
import sv.edu.udb.countriesweather.models.Country
import sv.edu.udb.countriesweather.ui.adapters.CountriesAdapter

class CountriesFragment: Fragment() {

    private val controller = CountriesController()
    private var region: String = "Americas"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        region = arguments?.getString("region") ?: "Americas"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_countries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.rvCountries)
        val progress = view.findViewById<ProgressBar>(R.id.progress)
        val tvError = view.findViewById<TextView>(R.id.tvError)

        rv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CountriesAdapter(emptyList()) { country ->
            findNavController().navigate(R.id.action_countries_to_detail, bundleOf("country" to country))
        }
        rv.adapter = adapter

        progress.visibility = View.VISIBLE
        tvError.visibility = View.GONE
        rv.visibility = View.GONE

        viewLifecycleOwner.lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) { controller.loadByRegion(region) }
            progress.visibility = View.GONE
            result.onSuccess { list ->
                rv.visibility = View.VISIBLE
                adapter.submit(list)
            }.onFailure { e ->
                tvError.visibility = View.VISIBLE
                tvError.text = getString(R.string.error) + ": " + (e.message ?: "Desconocido")
            }
        }
    }
}