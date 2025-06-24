package com.example.myeducainclusao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

data class AssociationItem(val imageResId: Int, val word: String)

class GameAssociationFragment : Fragment() {

    private lateinit var imageViewChallenge: ImageView
    private lateinit var textViewScore: TextView
    private lateinit var buttonOption1: Button
    private lateinit var buttonOption2: Button
    private lateinit var buttonOption3: Button

    private val items = listOf(
        AssociationItem(R.drawable.ic_game_apple, "Maçã"), // CRIE ESTES DRAWABLES
        AssociationItem(R.drawable.ic_game_ball, "Bola"),
        AssociationItem(R.drawable.ic_game_car, "Carro"),
        AssociationItem(R.drawable.ic_game_dog, "Cachorro")
    )
    private lateinit var currentItem: AssociationItem
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_association, container, false) // CRIE ESTE LAYOUT

        imageViewChallenge = view.findViewById(R.id.imageViewGameChallenge)
        textViewScore = view.findViewById(R.id.textViewGameScore)
        buttonOption1 = view.findViewById(R.id.buttonGameOption1)
        buttonOption2 = view.findViewById(R.id.buttonGameOption2)
        buttonOption3 = view.findViewById(R.id.buttonGameOption3)

        setupNewRound()

        buttonOption1.setOnClickListener { checkAnswer(buttonOption1.text.toString()) }
        buttonOption2.setOnClickListener { checkAnswer(buttonOption2.text.toString()) }
        buttonOption3.setOnClickListener { checkAnswer(buttonOption3.text.toString()) }

        return view
    }

    private fun setupNewRound() {
        if (items.isEmpty()) return
        currentItem = items.random()
        imageViewChallenge.setImageResource(currentItem.imageResId)

        val options = mutableListOf(currentItem.word)
        // Adicionar duas opções erradas diferentes
        while (options.size < 3) {
            val wrongItem = items.random()
            if (wrongItem.word != currentItem.word && !options.contains(wrongItem.word)) {
                options.add(wrongItem.word)
            }
            // Adicionar uma verificação para evitar loop infinito se houver poucos itens
            if (options.size < 3 && items.size < 3 && items.distinctBy { it.word }.size == options.size) break
        }
        options.shuffle() // Embaralhar as opções

        buttonOption1.text = options.getOrNull(0) ?: ""
        buttonOption2.text = options.getOrNull(1) ?: ""
        buttonOption3.text = options.getOrNull(2) ?: ""

        // Esconder botões se não houver opções suficientes (caso de poucos itens)
        buttonOption1.visibility = if (options.getOrNull(0) != null) View.VISIBLE else View.GONE
        buttonOption2.visibility = if (options.getOrNull(1) != null) View.VISIBLE else View.GONE
        buttonOption3.visibility = if (options.getOrNull(2) != null) View.VISIBLE else View.GONE

        updateScoreDisplay()
    }

    private fun checkAnswer(selectedWord: String) {
        if (selectedWord == currentItem.word) {
            score++
            Toast.makeText(context, "Correto!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Errado. A resposta era ${currentItem.word}", Toast.LENGTH_SHORT).show()
        }
        setupNewRound()
    }

    private fun updateScoreDisplay() {
        textViewScore.text = "Pontos: $score"
    }
}