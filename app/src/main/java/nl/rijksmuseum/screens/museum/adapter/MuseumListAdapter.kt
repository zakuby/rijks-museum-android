package nl.rijksmuseum.screens.museum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.rijksmuseum.databinding.ListItemMuseumBinding
import nl.rijksmuseum.models.MuseumArt

class MuseumListAdapter(
    val onClick: (String) -> Unit
) : RecyclerView.Adapter<MuseumListAdapter.ViewHolder>() {

    private var museumArts: List<MuseumArt> = emptyList()

    fun loadMuseumCollections(museums: List<MuseumArt>) {
        this.museumArts = museums
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMuseumBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = museumArts.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val museumArt = museumArts[position]
        holder.apply {
            bind(museumArt)
            itemView.setOnClickListener {
                museumArt.id ?: return@setOnClickListener
                onClick(museumArt.id)
            }
        }
    }

    inner class ViewHolder(
        private val binding: ListItemMuseumBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MuseumArt) = binding.apply { this.model = model }
    }
}