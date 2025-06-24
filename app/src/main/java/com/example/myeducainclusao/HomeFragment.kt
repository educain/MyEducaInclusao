package com.example.myeducainclusao // Mantenha o seu pacote se for diferente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
// Se R não estiver sendo resolvido automaticamente, adicione o import específico do seu pacote R
// import com.example.myeducainclusao.R

class HomeFragment : Fragment() {

    // Declaração das Views e FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var welcomeTextView: TextView
    private lateinit var logoutButton: Button
    private lateinit var routinesButton: Button
    private lateinit var communicationButton: Button
    // Adicione aqui outras Views se necessário, por exemplo:
    // private lateinit var socialStoriesButton: Button
    // private lateinit var behaviorReportButton: Button // Exemplo para o botão de relatório

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializa o Firebase Auth
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla o layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inicializa as Views
        welcomeTextView = view.findViewById(R.id.textViewWelcome)
        logoutButton = view.findViewById(R.id.buttonLogout)
        routinesButton = view.findViewById(R.id.buttonRoutines)
        communicationButton = view.findViewById(R.id.buttonCommunication)
        // Se adicionou outros botões, inicialize-os aqui também, por exemplo:
        // socialStoriesButton = view.findViewById(R.id.buttonSocialStories) // Certifique-se que o ID existe no XML
        // behaviorReportButton = view.findViewById(R.id.buttonAddBehaviorReport) // Certifique-se que o ID existe no XML

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Verifica se o usuário está logado
        val currentUser = auth.currentUser
        if (currentUser == null) {
            // Se não estiver logado, navega de volta para a tela de login
            // e finaliza a execução deste método para evitar NullPointerExceptions.
            // Certifique-se que R.id.loginFragment existe no seu nav_graph
            try {
                findNavController().navigate(R.id.loginFragment)
            } catch (e: IllegalStateException) {
                // Pode acontecer se o NavController não estiver pronto ou o fragmento não estiver mais anexado
                Toast.makeText(context, "Erro ao tentar navegar para o login.", Toast.LENGTH_SHORT).show()
            }
            return
        }

        // Define o texto de boas-vindas
        welcomeTextView.text = "Bem-vindo, ${currentUser.email ?: "Usuário"}!"

        // Configura o listener do botão de Logout
        logoutButton.setOnClickListener {
            auth.signOut()
            Toast.makeText(requireContext(), "Logout realizado.", Toast.LENGTH_SHORT).show()
            // Navega para a tela de login e limpa a pilha de volta para que o usuário
            // não possa voltar para a HomeFragment pressionando o botão "Voltar".
            try {
                findNavController().navigate(R.id.loginFragment) {
                    // Tente usar o ID do seu nav_graph principal se homeFragment não for o root.
                    // Ou o ID do fragmento de destino inicial do seu app.
                    popUpTo(R.id.nav_graph) { // ou R.id.homeFragment se for o início da pilha desta seção
                        inclusive = true
                    }
                    launchSingleTop = true // Evita múltiplas instâncias da tela de login
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Erro ao navegar para login após logout.", Toast.LENGTH_SHORT).show()
            }
        }

        // Configura o listener do botão de Rotinas
        routinesButton.setOnClickListener {
            // Navegar para a tela de Rotinas
            // Certifique-se que a ação action_homeFragment_to_routinesFragment existe no seu nav_graph.xml
            try {
                findNavController().navigate(R.id.action_homeFragment_to_routinesFragment)
            } catch (e: IllegalArgumentException) {
                Toast.makeText(requireContext(), "Rotinas: Destino não encontrado. Verifique o nav_graph.", Toast.LENGTH_LONG).show()
            }  catch (e: IllegalStateException) {
                Toast.makeText(requireContext(), "Rotinas: Erro de navegação.", Toast.LENGTH_LONG).show()
            }
        }

        // Configura o listener do botão de Comunicação (PECS)
        communicationButton.setOnClickListener {
            // Navegar para a tela de Comunicação (PECS)
            // Certifique-se que a ação action_homeFragment_to_pecsFragment existe no seu nav_graph.xml
            try {
                findNavController().navigate(R.id.action_homeFragment_to_pecsFragment)
            } catch (e: IllegalArgumentException) {
                Toast.makeText(requireContext(), "Comunicação: Destino não encontrado. Verifique o nav_graph.", Toast.LENGTH_LONG).show()
            } catch (e: IllegalStateException) {
                Toast.makeText(requireContext(), "Comunicação: Erro de navegação.", Toast.LENGTH_LONG).show()
            }
        }

        // Exemplo para um futuro botão de Histórias Sociais (descomente e adapte se necessário)
        /*
        socialStoriesButton.setOnClickListener {
            try {
                // Certifique-se que R.id.action_homeFragment_to_socialStoriesFragment existe no nav_graph
                findNavController().navigate(R.id.action_homeFragment_to_socialStoriesFragment)
            } catch (e: IllegalArgumentException) {
                Toast.makeText(requireContext(), "Hist. Sociais: Destino não encontrado.", Toast.LENGTH_LONG).show()
            } catch (e: IllegalStateException) {
                Toast.makeText(requireContext(), "Hist. Sociais: Erro de navegação.", Toast.LENGTH_LONG).show()
            }
        }
        */

        // Exemplo para um futuro botão de Adicionar Relatório Comportamental (descomente e adapte se necessário)
        /*
        behaviorReportButton.setOnClickListener {
            try {
                // Certifique-se que R.id.action_homeFragment_to_addBehaviorReportFragment existe no nav_graph
                findNavController().navigate(R.id.action_homeFragment_to_addBehaviorReportFragment)
            } catch (e: IllegalArgumentException) {
                Toast.makeText(requireContext(), "Relatório: Destino não encontrado.", Toast.LENGTH_LONG).show()
            } catch (e: IllegalStateException) {
                Toast.makeText(requireContext(), "Relatório: Erro de navegação.", Toast.LENGTH_LONG).show()
            }
        }
        */
    }
}