package com.example.myeducainclusao

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.layout.layout
import androidx.recyclerview.widget.RecyclerView
// import com.bumptech.glide.Glide // Exemplo se usar Glide para carregar imagens da URL

class RoutinesAdapter(private val routineItems: MutableList<RoutineItem>) :
    RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder>() {

    class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.imageViewRoutineIcon)
        val descriptionTextView: TextView = itemView.findViewById(R.id.textViewRoutineDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_routine, parent, false)
        return RoutineViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val currentItem = routineItems[position]
        holder.descriptionTextView.text = currentItem.description

        // Carregar imagem (exemplo básico, você precisará de uma biblioteca como Glide ou Coil para URLs)
        if (currentItem.imageUrl != null) {
            // Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.iconImageView)
            holder.iconImageView.setImageResource(R.drawable.ic_placeholder) // Placeholder
        } else {
            // Se estiver usando imageResId:
            // currentItem.imageResId?.let { holder.iconImageView.setImageResource(it) }
            holder.iconImageView.setImageResource(R.drawable.ic_placeholder) // Placeholder
        }
    }

    override fun getItemCount() = routineItems.size

    fun updateData(newItems: List<RoutineItem>) {
        routineItems.clear()
        routineItems.addAll(newItems)
        notifyDataSetChanged() // Para simplificar; DiffUtil seria mais eficiente
    }
}