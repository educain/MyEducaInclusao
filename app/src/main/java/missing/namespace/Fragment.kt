package com.example.myeducainclusao // Substitua pelo seu pacote real

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat // Para shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
// Se SensoryAlertHelper estiver no mesmo pacote, esta importação pode não ser necessária explicitamente
// import com.example.myeducainclusao.SensoryAlertHelper

// É recomendado que esta classe herde de Fragment
class SeuFragment : Fragment() { // Substitua SeuFragment pelo nome real da sua classe

    // 1. Declare o ActivityResultLauncher como uma propriedade da classe.
    //    Ele deve ser inicializado no construtor da classe, em um inicializador,
    //    ou em onCreate(), mas ANTES de onStart().
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialize o launcher aqui. Isso garante que ele está pronto antes que o Fragment
        // possa receber resultados.
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    // Permissão concedida, pode enviar notificações
                    Toast.makeText(requireContext(), "Permissão concedida!", Toast.LENGTH_SHORT).show()
                    // Exemplo: Chamar o helper para enviar a notificação
                    SensoryAlertHelper.sendSensoryAlert(
                        requireContext(),
                        "Alerta!",
                        "Permissão concedida. Lembre-se de respirar fundo."
                    )
                } else {
                    // Permissão negada, informe o usuário
                    Toast.makeText(
                        requireContext(), // Use requireContext() para garantir que não é nulo
                        "Permissão de notificação negada.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    // Você não precisa desta função personalizada, pois está usando a da biblioteca:
    // private fun registerForActivityResult(
    //    permission: ActivityResultContracts.RequestPermission,
    //    function: (Boolean) -> Unit
    // ) {
    //    TODO("Not yet implemented")
    // }

    private fun checkAndSendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // TIRAMISU == API 33
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(), // Use requireContext() aqui
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permissão já concedida
                    SensoryAlertHelper.sendSensoryAlert(
                        requireContext(),
                        "Alerta!",
                        "Lembre-se de respirar fundo."
                    )
                }
                // Use a função da ActivityCompat diretamente, passando a Activity do Fragment
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(), // Fornece a Activity
                    Manifest.permission.POST_NOTIFICATIONS
                ) -> {
                    // Mostrar uma UI explicando por que você precisa da permissão
                    // e então chamar requestPermissionLauncher.launch(...)
                    Toast.makeText(
                        requireContext(),
                        "Precisamos da permissão para enviar alertas.",
                        Toast.LENGTH_LONG
                    ).show()
                    // (Opcional) Mostrar um diálogo antes de pedir novamente
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                else -> {
                    // Pedir a permissão diretamente
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // Para versões anteriores ao Android 13, a permissão não é necessária em tempo de execução desta forma
            SensoryAlertHelper.sendSensoryAlert(
                requireContext(),
                "Alerta!",
                "Lembre-se de respirar fundo."
            )
        }
    }

    // Exemplo de como você poderia chamar esta função, por exemplo, de um clique de botão
    // fun onSomeButtonClick() {
    //     checkAndSendNotification()
    // }
}