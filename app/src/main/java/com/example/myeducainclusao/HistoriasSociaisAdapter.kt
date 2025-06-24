package com.example.myeducainclusao.features.historias // Substitua

// Importe seu item layout binding e a data class HistoriaSocial
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myeducainclusao.databinding.ItemHistoriaSocialBinding

class HistoriasSociaisAdapter(
    private val onItemClicked: (HistoriaSocial) -> Unit
) : ListAdapter<HistoriaSocial, HistoriasSociaisAdapter.HistoriaViewHolder>(HistoriaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriaViewHolder {
        val binding = ItemHistoriaSocialBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoriaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoriaViewHolder, position: Int) {
        val historia = getItem(position)
        holder.bind(historia)
        holder.itemView.setOnClickListener {
            onItemClicked(historia)
        }
    }

    class HistoriaViewHolder(private val binding: ItemHistoriaSocialBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(historia: HistoriaSocial) {
            binding.textViewItemHistoriaTitulo.text = historia.titulo
            // Se tiver imagem:
            // Glide.with(binding.imageViewItemHistoria.context).load(historia.imageUrl).into(binding.imageViewItemHistoria)
        }
    }
}

class HistoriaDiffCallback : DiffUtil.ItemCallback<HistoriaSocial>() {
    override fun areItemsTheSame(oldItem: HistoriaSocial, newItem: HistoriaSocial): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HistoriaSocial, newItem: HistoriaSocial): Boolean {
        return oldItem == newItem
    }
}