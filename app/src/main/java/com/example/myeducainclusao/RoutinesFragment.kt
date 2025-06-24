package com.example.myeducainclusao

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
// import androidx.compose.ui.layout.layout // Removido: Importação não utilizada do Jetpack Compose
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
// import com.google.firebase.auth.ktx.R // Removido: Importação incorreta da classe R
import com.example.myeducainclusao.R // Adicionado: Importação correta da classe R do seu projeto
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class RoutinesFragment : Fragment() {

    private lateinit var recyclerViewRoutines: RecyclerView
    private lateinit var addRoutineItemButton: FloatingActionButton
    private lateinit var routinesAdapter: RoutinesAdapter // Make sure RoutinesAdapter is defined and imported correctly
    private val routineItemsList = mutableListOf<RoutineItem>() // Make sure RoutineItem is defined and imported correctly

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_routines, container, false)

        // Initialize views using findViewById from the inflated view
        recyclerViewRoutines = view.findViewById(R.id.recyclerViewRoutines)
        addRoutineItemButton = view.findViewById(R.id.buttonAddRoutineItem)
        // Note: The .also block was removed as direct assignment is clearer here.

        setupRecyclerView()
        loadRoutineItems()

        addRoutineItemButton.setOnClickListener {
            // TODO: Open a dialog/screen to add a new routine item
            // Simple example of adding an item:
            addNewRoutineItem("Nova Tarefa ${routineItemsList.size + 1}", null)
        }
        return view
    }

    private fun setupRecyclerView() {
        // Ensure you have a valid context for LinearLayoutManager
        context?.let { ctx ->
            routinesAdapter = RoutinesAdapter(routineItemsList)
            recyclerViewRoutines.adapter = routinesAdapter
            recyclerViewRoutines.layoutManager = LinearLayoutManager(ctx)
        } ?: Log.e("RoutinesFragment", "Context is null in setupRecyclerView")
    }

    private fun loadRoutineItems() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            context?.let {
                Toast.makeText(it, "Usuário não logado.", Toast.LENGTH_SHORT).show()
            }
            // TODO: Navigate back to login or handle the error
            return
        }

        // Example: 'routines' collection and each document is a routine item for the user
        db.collection("users").document(currentUser.uid).collection("routineItems")
            .orderBy("order") // Sort by defined order
            .addSnapshotListener { snapshots, e -> // addSnapshotListener for real-time updates
                if (e != null) {
                    Log.w("RoutinesFragment", "Error loading routine items.", e)
                    context?.let {
                        Toast.makeText(it, "Erro ao carregar rotinas.", Toast.LENGTH_SHORT).show()
                    }
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    val items = snapshots.toObjects<RoutineItem>()
                    routineItemsList.clear()
                    routineItemsList.addAll(items)
                    if (::routinesAdapter.isInitialized) { // Check if adapter is initialized
                        routinesAdapter.notifyDataSetChanged() // For simplicity
                    }
                    Log.d("RoutinesFragment", "Routine items loaded: ${items.size}")
                }
            }
    }

    private fun addNewRoutineItem(description: String, imageUrl: String?) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            context?.let {
                Toast.makeText(it, "Usuário não logado para adicionar item.", Toast.LENGTH_SHORT).show()
            }
            return
        }

        val newItem = RoutineItem(
            description = description,
            imageUrl = imageUrl,
            order = routineItemsList.size, // Simple order, can be improved
            userId = currentUser.uid
            // id = "" // If your RoutineItem expects an ID here, Firestore generates it upon add
        )

        // Saves the new item to Firestore
        db.collection("users").document(currentUser.uid).collection("routineItems")
            .add(newItem) // .add() generates an automatic ID
            .addOnSuccessListener { documentReference ->
                Log.d("RoutinesFragment", "Routine item added with ID: ${documentReference.id}")
                // The SnapshotListener should already update the list
            }
            .addOnFailureListener { e ->
                Log.w("RoutinesFragment", "Error adding routine item", e)
                context?.let {
                    Toast.makeText(it, "Erro ao adicionar item.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

// Removed the problematic private fun ERROR.findViewById(...)v