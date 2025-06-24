package com.example.myeducainclusao

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddBehaviorReportFragment : Fragment() {

    private lateinit var spinnerMood: Spinner
    private lateinit var editTextBehaviorNotes: TextInputEditText
    private lateinit var editTextTriggers: TextInputEditText
    private lateinit var buttonSaveReport: Button

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    // Você precisará de uma forma de obter o ID do aluno selecionado
    private var currentStudentId: String = "SOME_STUDENT_ID" // Placeholder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_behavior_report, container, false)

        spinnerMood = view.findViewById(R.id.spinnerMood)
        editTextBehaviorNotes = view.findViewById(R.id.editTextBehaviorNotes)
        editTextTriggers = view.findViewById(R.id.editTextTriggers)
        buttonSaveReport = view.findViewById(R.id.buttonSaveReport)

        // Configurar Spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.mood_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMood.adapter = adapter
        }

        buttonSaveReport.setOnClickListener {
            saveReport()
        }
        return view
    }

    private fun saveReport() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(context, "Professor não logado.", Toast.LENGTH_SHORT).show()
            return
        }

        val mood = spinnerMood.selectedItem.toString()
        val notes = editTextBehaviorNotes.text.toString().trim()
        val triggers = editTextTriggers.text.toString().trim()

        if (notes.isEmpty()) {
            editTextBehaviorNotes.error = "Observações são obrigatórias"
            return
        }

        val report = BehaviorEntry(
            studentId = currentStudentId, // Substitua pelo ID real do aluno
            reporterId = currentUser.uid,
            date = Timestamp.now(),
            mood = mood,
            behaviorNotes = notes,
            triggers = if (triggers.isNotEmpty()) triggers else null
        )

        // Salvar no Firestore (ex: dentro do documento do aluno)
        // Estrutura: /students/{studentId}/behaviorReports/{reportId}
        db.collection("students").document(currentStudentId)
            .collection("behaviorReports")
            .add(report)
            .addOnSuccessListener {
                Toast.makeText(context, "Relatório salvo com sucesso!", Toast.LENGTH_SHORT).show()
                Log.d("AddBehaviorReport", "Relatório salvo com ID: ${it.id}")
                findNavController().popBackStack() // Volta para a tela anterior
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Erro ao salvar relatório: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("AddBehaviorReport", "Erro ao salvar", e)
            }
    }
}