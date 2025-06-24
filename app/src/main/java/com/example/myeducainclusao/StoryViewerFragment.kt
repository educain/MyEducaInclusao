package com.example.myeducainclusao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter // OU RecyclerView.Adapter
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
// import com.bumptech.glide.Glide // Para carregar imagens

class StoryViewerFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var storyId: String // Recebido via argumentos de navegação
    private val db = Firebase.firestore
    private var storyPages = mutableListOf<StoryPage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            storyId = it.getString("story_id_arg") ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_story_viewer, container, false) // CRIE ESTE LAYOUT
        viewPager = view.findViewById(R.id.viewPagerStory)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (storyId.isNotEmpty()) {
            loadStoryPages()
        } else {
            Toast.makeText(context, "ID da história não encontrado.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadStoryPages() {
        // Estrutura no Firestore: /socialStories/{storyId}/pages/{pageDocumentId} (ordenado por pageNumber)
        db.collection("socialStories").document(storyId).collection("pages")
            .orderBy("pageNumber")
            .get()
            .addOnSuccessListener { documents ->
                if (documents != null && !documents.isEmpty) {
                    storyPages.clear()
                    for (document in documents) {
                        document.toObject<StoryPage>()?.let { storyPages.add(it) }
                    }
                    viewPager.adapter = StoryPagesAdapter(storyPages) // CRIE ESTE ADAPTER
                } else {
                    Toast.makeText(context, "Nenhuma página encontrada para esta história.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Erro ao carregar páginas da história.", Toast.LENGTH_SHORT).show()
            }
    }
}

// Adapter para o ViewPager2 (usando RecyclerView.Adapter)
class StoryPagesAdapter(private val pages: List<StoryPage>) :
    RecyclerView.Adapter<StoryPagesAdapter.StoryPageViewHolder>() {

    class StoryPageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pageText: TextView = itemView.findViewById(R.id.textViewStoryPageText)
        val pageImage: ImageView = itemView.findViewById(R.id.imageViewStoryPageImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryPageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_story_page, parent, false) // CRIE ESTE LAYOUT DE ITEM
        return StoryPageViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryPageViewHolder, position: Int) {
        val page = pages[position]
        holder.pageText.text = page.text
        if (!page.imageUrl.isNullOrEmpty()) {
            // Glide.with(holder.itemView.context).load(page.imageUrl).into(holder.pageImage)
            holder.pageImage.setImageResource(R.drawable.ic_placeholder) // Placeholder
            holder.pageImage.visibility = View.VISIBLE
        } else {
            holder.pageImage.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = pages.size
}