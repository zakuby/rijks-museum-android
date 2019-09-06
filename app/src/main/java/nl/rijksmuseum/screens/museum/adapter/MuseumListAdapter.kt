package nl.rijksmuseum.screens.museum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.rijksmuseum.databinding.ListItemMuseumBinding
import nl.rijksmuseum.models.Museum

class MuseumListAdapter : RecyclerView.Adapter<MuseumListAdapter.ViewHolder>() {

    private var museums: List<Museum> = emptyList()

    fun loadMuseumCollections(museums: List<Museum>) {
        this.museums = museums
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMuseumBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = museums.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val museum = museums[position]
        holder.bind(museum)
    }


    inner class ViewHolder(
        private val binding: ListItemMuseumBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Museum) = binding.apply { this.model = model }
    }

}