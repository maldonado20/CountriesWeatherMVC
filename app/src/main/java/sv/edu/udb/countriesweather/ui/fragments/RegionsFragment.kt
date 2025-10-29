package sv.edu.udb.countriesweather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.udb.countriesweather.R
import sv.edu.udb.countriesweather.controllers.RegionsController
import sv.edu.udb.countriesweather.ui.adapters.RegionsAdapter

class RegionsFragment : Fragment() {

    private val controller = RegionsController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_regions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.rvRegions)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = RegionsAdapter(controller.regions()) { region ->
            findNavController().navigate(R.id.action_regions_to_countries, bundleOf("region" to region))
        }
    }
}