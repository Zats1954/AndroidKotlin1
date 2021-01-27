package ru.netology.nmedia.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import java.lang.Double.valueOf
import java.lang.Float.valueOf
import java.sql.Date.valueOf
import java.time.chrono.JapaneseEra.valueOf

class FCMService: FirebaseMessagingService() {
    private val action = "action"
    private val content = "content"
    private val channelId = "remote"
    private val gson = Gson()

    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT >= VERSION_CODES.O){
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply{
                 description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.data[action]?.let{
            when(NotificationCompat.Action.valueOf(it)){
                NotificationCompat.Action.LIKE - handleLike(gson.fromJson(message.data[content], Like::class.java))
            }
        }
    }
    private fun handleLike(content: Like){
        
    }

    override fun onNewToken(token: String) {
        print(token)
    }
}