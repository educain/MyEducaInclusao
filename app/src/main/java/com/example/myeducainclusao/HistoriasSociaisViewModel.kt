package com.example.myeducainclusao.features.historias // Substitua

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// Data class para representar uma história social (coloque em um arquivo model)
data class HistoriaSocial(
    val id: String = "", // Para o ID do documento do Firestore
    val titulo: String = "",
    val conteudo: String = "", // Ou uma lista de páginas, imagens, etc.
    val imageUrl: String? = null
)

class HistoriasSociaisViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _historias = MutableStateFlow<List<HistoriaSocial>>(emptyList())
    val historias: StateFlow<List<HistoriaSocial>> = _historias

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun carregarHistorias() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = db.collection("historiasSociais") // Sua coleção no Firestore
                    .get()
                    .await() // Requer kotlinx-coroutines-play-services

                val listaHistorias = result.documents.mapNotNull { doc ->
                    val historia = doc.toObject(HistoriaSocial::class.java)
                    historia?.copy(id = doc.id) // Adiciona o ID do documento
                }
                _historias.value = listaHistorias

            } catch (e: Exception) {
                _error.value = "Erro ao carregar histórias: ${e.message}"
                // Log.e("HistoriasVM", "Erro: ", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}