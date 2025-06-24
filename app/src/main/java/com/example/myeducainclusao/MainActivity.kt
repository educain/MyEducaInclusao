package com.example.myeducainclusao // Certifique-se que este é o nome correto do seu pacote

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myeducainclusao.R // IMPORTANTE: Importação correta do R do seu projeto

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main) // Define o layout da Activity diretamente

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController // Atribui ao membro da classe

        // Configura a AppBarConfiguration com os destinos de nível superior do seu gráfico de navegação.
        // Se seu nav_graph não define um label para o startDestination, a AppBar pode ficar sem título.
        // É importante definir os top-level destinations se você tiver um DrawerLayout,
        // para que o ícone do menu (hamburger) apareça em vez do botão "up".
        // Se você não tem um DrawerLayout, pode passar apenas navController.graph.
        appBarConfiguration = AppBarConfiguration(navController.graph) // Atribui ao membro da classe
        setupActionBarWithNavController(navController, appBarConfiguration)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Para o botão "voltar" da ActionBar (se houver) funcionar com o Navigation Component
    override fun onSupportNavigateUp(): Boolean {
        // Usa o NavigationUI.navigateUp para garantir a integração correta com a appBarConfiguration
        // e o comportamento padrão da ActionBar.
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}