package com.example.myeducainclusao

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

object SensoryAlertHelper {

    private const val CHANNEL_ID = "sensory_alerts_channel"
    private const val CHANNEL_NAME = "Alertas Sensoriais"
    private const val NOTIFICATION_ID_BASE = 1000 // Para gerar IDs únicos

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH // Alta importância para alertas
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = "Canal para alertas e sugestões sensoriais"
                // Configurações adicionais do canal, como vibração, som, etc.
                enableLights(true)
                enableVibration(true)
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendSensoryAlert(
        context: Context,
        title: String,
        message: String,
        notificationId: Int = (NOTIFICATION_ID_BASE + System.currentTimeMillis() % 10000).toInt() // ID dinâmico simples
    ) {
        // Criar um intent para abrir o app quando a notificação for tocada (opcional)
        val intent = Intent(context, MainActivity::class.java).apply { // Altere MainActivity se necessário
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_alert) // CRIE ESTE ÍCONE em res/drawable
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent) // Ação ao tocar
            .setAutoCancel(true) // Remove a notificação quando tocada
        // .addAction(R.drawable.ic_action_calm, "Ação Calmante", pendingIntentActionCalm) // Exemplo de ação

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, builder.build())

    }
}