package com.example.myeducainclusao

import com.google.firebase.firestore.DocumentId

data class SocialStory(
    @DocumentId var id: String? = null,
    var title: String = "",
    var author: String? = null,
    var coverImageUrl: String? = null
    // Outros metadados, como categoria
) {
    constructor() : this(null, "", null, null)
}

data class StoryPage(
    var pageNumber: Int = 0,
    var text: String = "",
    var imageUrl: String? = null // URL da imagem para a página
    // Ou imageResId: Int?
) {
    constructor() : this(0, "", null)
}