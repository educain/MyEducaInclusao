package com.example.myeducainclusao

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.layout.layout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import java.util.Locale

class PecsFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var recyclerViewPecs: RecyclerView
    private lateinit var pecsAdapter: PecsAdapter
    private var pecsCardsList = mutableListOf<PecsCard>()
    private var tts: TextToSpeech? = null

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializar TTS
        tts = TextToSpeech(context, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pecs, container, false)
        recyclerViewPecs = view.findViewById(R.id.recyclerViewPecsCards)

        setupRecyclerView()
        loadPecsCards() // Carregar dados do Firestore ou de uma lista estática
        return view
    }

    private fun setupRecyclerView() {
        // Ajuste o número de colunas conforme necessário
        recyclerViewPecs.layoutManager = GridLayoutManager(context, 3)
        pecsAdapter = PecsAdapter(pecsCardsList) { cardClicked ->
            speakOut(cardClicked.label)
        }
        recyclerViewPecs.adapter = pecsAdapter
    }

    private fun loadPecsCards() {
        // Exemplo carregando de uma coleção "pecsCards" no Firestore
        // Você pode estruturar isso por categorias, etc.
        db.collection("pecsGlobalCards") // Ou uma coleção específica do usuário
            .get()
            .addOnSuccessListener { documents ->
                if (documents != null && !documents.isEmpty) {
                    pecsCardsList.clear()
                    pecsCardsList.addAll(documents.toObjects())
                    pecsAdapter.notifyDataSetChanged()
                    Log.d("PecsFragment", "Cartões PECS carregados: ${pecsCardsList.size}")
                } else {
                    Log.d("PecsFragment", "Nenhum cartão PECS encontrado.")
                    // Adicionar alguns cartões padrão se estiver vazio para teste
                    loadDefaultPecsCards()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("PecsFragment", "Erro ao carregar cartões PECS.", exception)
                Toast.makeText(context, "Erro ao carregar cartões.", Toast.LENGTH_SHORT).show()
                loadDefaultPecsCards() // Carrega padrão em caso de falha
            }
    }

    private fun loadDefaultPecsCards() {
        // Exemplo de dados padrão (você pode carregar do Firestore)
        pecsCardsList.clear()
        pecsCardsList.add(PecsCard("1", "Eu quero", null))
        pecsCardsList.add(PecsCard("2", "Brincar", null))
        pecsCardsList.add(PecsCard("3", "Comer", null))
        pecsCardsList.add(PecsCard("4", "Banheiro", null))
        pecsCardsList.add(PecsCard("5", "Sim", null))
        pecsCardsList.add(PecsCard("6", "Não", null))
        pecsAdapter.notifyDataSetChanged()
    }


    // --- TextToSpeech Implementation ---
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Defina o idioma, por exemplo, Português do Brasil
            val result = tts?.setLanguage(Locale("pt", "BR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "O Idioma especificado não é suportado!")
                Toast.makeText(context, "TTS: Idioma não suportado.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.e("TTS", "Falha na inicialização do TTS!")
            Toast.makeText(context, "TTS: Falha na inicialização.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        // Libera o TTS quando o Fragment é destruído
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}