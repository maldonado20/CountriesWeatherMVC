package sv.edu.udb.countriesweather.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import sv.edu.udb.countriesweather.R
import sv.edu.udb.countriesweather.models.Country

class CountriesAdapter(
    private var items: List<Country>,
    private val onClick: (Country) -> Unit
): RecyclerView.Adapter<CountriesAdapter.VH>() {

    inner class VH(v: View): RecyclerView.ViewHolder(v) {
        val iv: ImageView = v.findViewById(R.id.ivFlag)
        val tvName: TextView = v.findViewById(R.id.tvName)
        val tvCapital: TextView = v.findViewById(R.id.tvCapital)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return VH(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = items[position]
        holder.tvName.text = c.name.common ?: "N/D"
        holder.tvCapital.text = holder.itemView.context.getString(R.string.capital) + ": " + (c.capital?.firstOrNull() ?: "N/D")
        val flagUrl = c.flags?.png ?: c.flags?.svg
        holder.iv.load(flagUrl)
        holder.itemView.setOnClickListener { onClick(c) }
    }

    fun submit(newItems: List<Country>) {
        items = newItems
        notifyDataSetChanged()
    }
}