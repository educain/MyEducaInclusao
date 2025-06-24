package com.example.myeducainclusao

import com.google.firebase.firestore.DocumentId // Para mapear o ID do documento automaticamente

data class RoutineItem(
    @DocumentId // Mapeia o ID do documento do Firestore para este campo
    var id: String? = null, // ID do item da rotina (gerado pelo Firestore ou customizado)
    var description: String = "", // Descrição da tarefa
    var imageUrl: String? = null, // URL da imagem (se usar imagens da web/storage)
    // Ou var imageResId: Int? = null, // Se usar imagens dos recursos do app (drawables)
    var order: Int = 0, // Ordem do item na rotina
    var userId: String? = null // Para associar a rotina a um usuário/aluno específico
) {
    // Construtor vazio necessário para o Firestore
    constructor() : this(null, "", null, 0, null)
}