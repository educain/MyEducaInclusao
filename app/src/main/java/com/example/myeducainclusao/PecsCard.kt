package com.example.myeducainclusao

data class PecsCard(
    val id: String,
    val label: String, // Texto do cartão
    val imageUrl: String? = null, // Ou imageResId: Int? se for usar um recurso drawable
    // val audioUrl: String? = null // Se tiver áudios específicos para cada cartão
)