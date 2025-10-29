package sv.edu.udb.countriesweather.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sv.edu.udb.countriesweather.R

class RegionsAdapter(
    private val items: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<RegionsAdapter.VH>() {

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.tvRegion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_region, parent, false)
        return VH(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val region = items[position]
        holder.tv.text = region
        holder.itemView.setOnClickListener { onClick(region) }
    }
}