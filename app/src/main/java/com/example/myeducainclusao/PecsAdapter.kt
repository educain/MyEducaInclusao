package com.example.myeducainclusao

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
// import androidx.compose.ui.layout.layout // Removido: Esta importação não parece estar sendo utilizada aqui
import androidx.recyclerview.widget.RecyclerView
// import com.google.firebase.auth.ktx.R // Removido: Importação incorreta da classe R
import com.example.myeducainclusao.R // Adicionado: Importação correta da classe R do seu projeto

class PecsAdapter(
    private val pecsCards: List<PecsCard>,
    private val onItemClick: (PecsCard) -> Unit // Callback para o clique
) : RecyclerView.Adapter<PecsAdapter.PecsViewHolder>() {

    class PecsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewPecsImage)
        val labelView: TextView = itemView.findViewById(R.id.textViewPecsLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PecsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pecs_card, parent, false)
        return PecsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PecsViewHolder, position: Int) {
        val card = pecsCards[position]
        holder.labelView.text = card.label
        // Carregar imagem (similar ao RoutinesAdapter)
        // TODO: Implementar carregamento de imagem real com Glide ou Coil se necessário
        if (card.imageUrl != null) {
            // Exemplo com Glide (você precisaria da dependência e de um contexto, ex: holder.itemView.context)
            // Glide.with(holder.itemView.context).load(card.imageUrl).placeholder(R.drawable.ic_placeholder).into(holder.imageView)
            holder.imageView.setImageResource(R.drawable.ic_placeholder) // Placeholder atual
        } else {
            holder.imageView.setImageResource(R.drawable.ic_placeholder)
        }

        holder.itemView.setOnClickListener {
            onItemClick(card)
        }
    }

    override fun getItemCount() = pecsCards.size
}