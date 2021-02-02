package ru.netology.nmedia.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.LikeActivity
import ru.netology.nmedia.activity.NotifyActivity
import ru.netology.nmedia.dto.Like
import ru.netology.nmedia.dto.Post
import kotlin.random.Random


class FCMService : FirebaseMessagingService() {
    private val action = "action"
    private val content = "content"
    private val channelId = "remote"
    private val gson = Gson()
    private val TAG = "NMedia"

    override fun onCreate() {
        super.onCreate()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.data[action]?.let {
            Action.values().find { it1 -> it1.name == it }?.apply {
                try{
                when (this) {
                    Action.LIKE ->
                        handleLike(gson.fromJson(message.data[content], Like::class.java))
                    Action.POST ->
                        handlePost(gson.fromJson(message.data[content], Post::class.java))
                }
                } catch(e: JsonSyntaxException){
                    Log.d(TAG, "Неправильный формат данных нотификации ${this.name} от ${message.from}")
                }
            }
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "!!!token: $token ")
        print("This is my token $token ")
    }

    private fun handleLike(content: Like) {
        val resultIntent = Intent(this, LikeActivity::class.java).apply{
            putExtra("post_bundle", Bundle().apply{ putParcelable ("like", content)})
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)}
        val resultPendingIntent = PendingIntent.getActivity(
            this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat.Builder(this, channelId)
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                "User ${content.userName}  liked ${content.postAuthor} "
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(resultPendingIntent)
            .build()
        print(content.postId.toString() + " " + content.postAuthor)
        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    private fun handlePost(content: Post) {
        val resultIntent = Intent(this, NotifyActivity::class.java).apply{
        putExtra("post_bundle", Bundle().apply{ putParcelable ("post", content)})
                 addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)}
        val resultPendingIntent = PendingIntent.getActivity(
            this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                "User ${content.author}  wrote ${content.published} "
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(resultPendingIntent)
            .build()
        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    enum class Action {
        LIKE,
        POST
    }
}