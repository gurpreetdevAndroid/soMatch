package com.devstory.somatch.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.devstory.somatch.R
import com.devstory.somatch.repository.preferences.UserPrefsManager
import com.devstory.somatch.utils.GeneralFunctions
import com.devstory.somatch.views.activities.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {

        // Push notification types
        const val PUSH_TYPE_FOLLOW_REQUESTS = "1"
        const val PUSH_TYPE_POST = "2"
        const val PUSH_TYPE_CHAT_MESSAGE = "3"
        const val PUSH_TYPE_ADMIN = "4"

        private const val PARAM_KEY_PUSH_TYPE = "push_type"
        private const val PARAM_KEY_MESSAGE_TO_DISPLAY = "message_to_display"
        private const val PARAM_KEY_USER = "user"
        private const val PARAM_KEY_POST = "post"
        private const val PARAM_KEY_CONVERSATION = "conversation"
        private const val PARAM_KEY_UNREAD_NOTIFICATIONS_COUNT = "unread_notifications_count"
        private const val PARAM_KEY_UNREAD_CONVERSATIONS_COUNT = "unread_conversations_count"


        // Notification channel data
        private const val PACKAGE_NAME = "com.emotic.app"

        private const val CHANNEL_ID_FOLLOW_REQUEST_NOTIFICATIONS = "$PACKAGE_NAME.followersNotifications"
        private const val CHANNEL_NAME_FOLLOW_REQUEST_NOTIFICATIONS = "Follow Requests"

        private const val CHANNEL_ID_POST_NOTIFICATIONS = "$PACKAGE_NAME.postNotifications"
        private const val CHANNEL_NAME_POST_NOTIFICATIONS = "Post"

        private const val CHANNEL_ID_CHAT_NOTIFICATIONS = "$PACKAGE_NAME.chatNotifications"
        private const val CHANNEL_NAME_CHAT_NOTIFICATIONS = "Chat"

        private const val CHANNEL_ID_ADMIN_NOTIFICATIONS = "$PACKAGE_NAME.adminNotifications"
        private const val CHANNEL_NAME_ADMIN_NOTIFICATIONS = "Admin"

    }


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String?) {

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token)
    }

    private var mNotificationManager: NotificationManager? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        val mapData = remoteMessage?.data
        if (null != mapData && mapData.isNotEmpty()) {

            if (null == mNotificationManager) {
                mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager
            }

            if (mapData.containsKey(PARAM_KEY_PUSH_TYPE) &&
                    mapData.containsKey(PARAM_KEY_MESSAGE_TO_DISPLAY)) {
                if (UserPrefsManager(this).isLogined) {
                    when (mapData[PARAM_KEY_PUSH_TYPE]) {
                        PUSH_TYPE_FOLLOW_REQUESTS -> {
//                            sendFollowRequestNotifications(mapData[PARAM_KEY_USER] ?: "",
//                                    mapData[PARAM_KEY_MESSAGE_TO_DISPLAY] ?: "",
//                                    CHANNEL_ID_FOLLOW_REQUEST_NOTIFICATIONS,
//                                    CHANNEL_NAME_FOLLOW_REQUEST_NOTIFICATIONS)
                        }
                        PUSH_TYPE_POST -> {
//                            sendPostsNotifications(mapData[PARAM_KEY_POST] ?: "",
//                                    mapData[PARAM_KEY_MESSAGE_TO_DISPLAY] ?: "",
//                                    mapData[PARAM_KEY_UNREAD_NOTIFICATIONS_COUNT] ?: "",
//                                    CHANNEL_ID_POST_NOTIFICATIONS,
//                                    CHANNEL_NAME_POST_NOTIFICATIONS)
                        }
                        PUSH_TYPE_CHAT_MESSAGE -> {
//                            sendMessageNotification(mapData[PARAM_KEY_CONVERSATION] ?: "",
//                                    mapData[PARAM_KEY_MESSAGE_TO_DISPLAY] ?: "",
//                                    mapData[PARAM_KEY_UNREAD_CONVERSATIONS_COUNT] ?: "",
//                                    CHANNEL_ID_CHAT_NOTIFICATIONS,
//                                    CHANNEL_NAME_CHAT_NOTIFICATIONS)
                        }
                        else -> {
                            sendGeneralNotification(mapData[PARAM_KEY_MESSAGE_TO_DISPLAY] ?: "",
                                    CHANNEL_ID_ADMIN_NOTIFICATIONS,
                                    CHANNEL_NAME_ADMIN_NOTIFICATIONS)
                        }
                    }
                } else if (PUSH_TYPE_ADMIN == mapData[PARAM_KEY_PUSH_TYPE]) {
                    sendGeneralNotification(mapData[PARAM_KEY_MESSAGE_TO_DISPLAY] ?: "",
                            CHANNEL_ID_ADMIN_NOTIFICATIONS,
                            CHANNEL_NAME_ADMIN_NOTIFICATIONS)
                }

            }
        }
    }

//    private fun sendPostsNotifications(feedJSONString: String, messageToDisplay: String,
//                                       unreadNotifCount: String, channelId: String,
//                                       channelName: String) {
//
//        Gson().fromJson(feedJSONString, Feed::class.java)?.let { feed ->
//            with(feed) {
//                val intent = Intent(this@MyFirebaseMessagingService, FeedDetailActivity::class.java)
//
//                intent.putExtra(FeedDetailActivity.INTENT_EXTRAS_FEED, feed)
//                intent.putExtra(BaseAppCompactActivity.INTENT_EXTRAS_IS_FROM_NOTIFICATION, true)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//                mNotificationManager?.notify(id, getNotification(contentMessage = messageToDisplay,
//                        pendingIntent = PendingIntent
//                                .getActivity(this@MyFirebaseMessagingService, id, intent,
//                                        PendingIntent.FLAG_UPDATE_CURRENT),
//                        channelId = channelId,
//                        channelName = channelName))
//
//                // Send broadcast to update unread notifications count
////            LocalBroadcastManager.getInstance(this).sendBroadcast(
////                    Intent(NotificationsFragment.INTENT_FILTER_UPDATE_UNREAD_NOTIF_COUNT)
////                            .putExtra(NotificationsFragment.INTENT_EXTRAS_UNREAD_NOTIF_COUNT,
////                                    if (unreadNotifCount.isEmpty()) 0 else unreadNotifCount.toInt()))
//            }
//        }
//    }

//    private fun sendMessageNotification(messageJSONString: String, messageToDisplay: String,
//                                        unreadConvCount: String, channelId: String,
//                                        channelName: String) {
//
//        Gson().fromJson(messageJSONString, Conversation::class.java)?.let { conversation ->
//            with(conversation) {
//                if (id != ApplicationGlobal.inChatConversationId) {
//
//                    val intent = Intent(this@MyFirebaseMessagingService,
//                            ChatActivity::class.java)
//                            .putExtra(ChatActivity.INTENT_EXTRAS_CONVERSATION, conversation)
//                            .putExtra(BaseAppCompactActivity.INTENT_EXTRAS_IS_FROM_NOTIFICATION, true)
//                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
//
//                    mNotificationManager?.notify(id, getNotification(contentMessage = messageToDisplay,
//                            pendingIntent = PendingIntent
//                                    .getActivity(this@MyFirebaseMessagingService,
//                                            id, intent, PendingIntent.FLAG_UPDATE_CURRENT),
//                            channelId = channelId,
//                            channelName = channelName))
//
////                // Send broadcast to update unread conversations count
////                val unreadConvCount = data[PARAM_KEY_UNREAD_CONVERSATIONS_COUNT] ?: "0"
////                LocalBroadcastManager.getInstance(this).sendBroadcast(
////                        Intent(EditProfileFragment.INTENT_FILTER_UPDATE_PROFILE)
////                                .putExtra(ProfileFragment.INTENT_EXTRAS_UNREAD_CONV_COUNT,
////                                        if (unreadConvCount.isEmpty()) 0 else unreadConvCount.toInt()))
//
//                }
//            }
//        }
//    }

    private fun sendGeneralNotification(messageToDisplay: String,
                                        channelId: String, channelName: String) {

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        mNotificationManager?.notify(0, getNotification(contentMessage = messageToDisplay,
                pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT),
                channelId = channelId,
                channelName = channelName))
    }

    @SuppressLint("NewApi")
    private fun getNotification(contentTitle: String = getString(R.string.app_name),
                                contentMessage: String, pendingIntent: PendingIntent,
                                channelId: String, channelName: String): Notification {

//        val sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
//                packageName + "/" + R.raw.notification_sound)

        if (isOreoDevice && null == mNotificationManager?.getNotificationChannel(channelId)) {
            val notificationChannel = NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.setShowBadge(true)
//            notificationChannel.setSound(sound, AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_NOTIFICATION).build())
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            mNotificationManager?.createNotificationChannel(notificationChannel)
        }
        return NotificationCompat.Builder(
                this, channelId)
                .setContentTitle(contentTitle)
                .setContentText(contentMessage)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(contentMessage))
                .setSmallIcon(getNotificationIcon())
                .setTicker(contentTitle)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
//                .setSound(sound)
                .setDefaults(Notification.DEFAULT_LIGHTS and Notification.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true).build()
    }

    private val isOreoDevice: Boolean
        get() = android.os.Build.VERSION_CODES.O <= android.os.Build.VERSION.SDK_INT

    private fun getNotificationIcon(): Int {
        return if (GeneralFunctions.isAboveLollipopDevice)
            R.mipmap.ic_launcher
        else
            R.mipmap.ic_launcher
    }
}