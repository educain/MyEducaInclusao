package com.example.myeducainclusao

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class AlertsConfigurationFragment : Fragment() {

    private lateinit var editTextAlertTitle: EditText
    private lateinit var editTextAlertMessage: EditText
    private lateinit var buttonSendAlert: Button

    // Para permissão de notificação (Android 13+)
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(context, "Permissão concedida. Tente enviar o alerta novamente.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permissão de notificação negada.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alerts_configuration, container, false) // CRIE ESTE LAYOUT

        // IMPORTANTE: Crie o canal de notificação uma vez (ex: na Application class ou MainActivity)
        SensoryAlertHelper.createNotificationChannel(requireContext().applicationContext)

        editTextAlertTitle = view.findViewById(R.id.editTextAlertTitle)
        editTextAlertMessage = view.findViewById(R.id.editTextAlertMessage)
        buttonSendAlert = view.findViewById(R.id.buttonSendTestAlert)

        buttonSendAlert.setOnClickListener {
            val title = editTextAlertTitle.text.toString().trim()
            val message = editTextAlertMessage.text.toString().trim()

            if (title.isNotEmpty() && message.isNotEmpty()) {
                checkAndSendNotification(title, message)
            } else {
                Toast.makeText(context, "Preencha o título e a mensagem.", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun checkAndSendNotification(title: String, message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permissão já concedida
                    SensoryAlertHelper.sendSensoryAlert(requireContext(), title, message)
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    // Mostrar uma UI explicando por que você precisa da permissão e então pedir
                    // Por exemplo, um AlertDialog
                    Toast.makeText(context, "Precisamos da permissão para enviar alertas importantes.", Toast.LENGTH_LONG).show()
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                else -> {
                    // Pedir a permissão diretamente
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // Para versões anteriores ao Android 13
            SensoryAlertHelper.sendSensoryAlert(requireContext(), title, message)
        }
    }
}