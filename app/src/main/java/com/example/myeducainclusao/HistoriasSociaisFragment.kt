package com.example.myeducainclusao.features.historias // Substitua

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
// Importe sua classe de Binding gerada e o Adapter
import com.example.myeducainclusao.databinding.FragmentHistoriasSociaisBinding
import kotlinx.coroutines.launch

class HistoriasSociaisFragment : Fragment() {

    private var _binding: FragmentHistoriasSociaisBinding? = null
    private val binding get() = _binding!! // Só use entre onCreateView e onDestroyView

    private val viewModel: HistoriasSociaisViewModel by viewModels()
    private lateinit var historiasAdapter: HistoriasSociaisAdapter // CRIE ESTE ADAPTER

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoriasSociaisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView() // Configure seu RecyclerView
        observeViewModel()

        viewModel.carregarHistorias() // Chame a função para carregar os dados
    }

    private fun setupRecyclerView() {
        historiasAdapter = HistoriasSociaisAdapter { historia ->
            // Ação ao clicar em uma história (navegar para detalhes, por exemplo)
            Toast.makeText(context, "Clicou em: ${historia.titulo}", Toast.LENGTH_SHORT).show()
            // findNavController().navigate(HistoriasSociaisFragmentDirections.actionParaDetalhes(historia.id))
        }
        binding.recyclerViewHistorias.apply {
            adapter = historiasAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.historias.collect { lista ->
                        historiasAdapter.submitList(lista) // Se usar ListAdapter
                    }
                }
                launch {
                    viewModel.isLoading.collect { isLoading ->
                        binding.progressBarHistorias.visibility = if (isLoading) View.VISIBLE else View.GONE
                    }
                }
                launch {
                    viewModel.error.collect { errorMsg ->
                        errorMsg?.let {
                            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita memory leaks
    }
}