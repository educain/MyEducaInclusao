package com.example.myeducainclusao

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle // Necessário para onCreate
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher // Importação explícita
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class Fragment() { // Substitua SeuFragment pelo nome real da sua classe

    // Declaração do ActivityResultLauncher
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialize o launcher aqui.
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    // Permissão concedida, pode enviar notificações
                    SensoryAlertHelper.sendSensoryAlert(requireContext(), "Alerta!", "Lembre-se de respirar fundo.")
                    Toast.makeText(requireContext(), "Permissão concedida!", Toast.LENGTH_SHORT).show()
                } else {
                    // Permissão negada, informe o usuário
                    Toast.makeText(requireContext(), "Permissão de notificação negada.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkAndSendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // TIRAMISU == API 33
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    SensoryAlertHelper.sendSensoryAlert(requireContext(), "Alerta!", "Lembre-se de respirar fundo.")
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) -> {
                    Toast.makeText(requireContext(), "Precisamos da permissão para enviar alertas.", Toast.LENGTH_LONG).show()
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            SensoryAlertHelper.sendSensoryAlert(requireContext(), "Alerta!", "Lembre-se de respirar fundo.")
        }
    }

    // Exemplo de como você poderia chamar checkAndSendNotification
    // fun onSendNotificationClicked() {
    //     checkAndSendNotification()
    // }
}