package com.example.myeducainclusao

import com.google.firebase.Timestamp // Para data e hora
import com.google.firebase.firestore.DocumentId

data class BehaviorEntry(
    @DocumentId
    var id: String? = null,
    var studentId: String, // ID do aluno
    var reporterId: String, // ID de quem reportou (professor)
    var date: Timestamp = Timestamp.now(), // Data e hora do registro
    var mood: String, // Ex: "Feliz", "Triste", "Irritado", "Calmo"
    var behaviorNotes: String, // Observações sobre o comportamento
    var triggers: String? = null, // Possíveis gatilhos
    var strategiesUsed: String? = null // Estratégias que ajudaram
) {
    constructor() : this(null, "", "", Timestamp.now(), "", "", null, null)
}