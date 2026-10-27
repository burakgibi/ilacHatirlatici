package com.example.ilachatirlatici;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID_SOUND = "MEDICINE_SOUND_CHANNEL_LOUD";
    private static final String CHANNEL_ID_VIBE = "MEDICINE_VIBE_CHANNEL_LOUD";

    @Override
    public void onReceive(Context context, Intent intent) {
        int medId = intent.getIntExtra("MED_ID", 0);
        String name = intent.getStringExtra("MED_NAME");
        String dosage = intent.getStringExtra("MED_DOSAGE");
        int offset = intent.getIntExtra("TIME_OFFSET", 0);
        boolean soundEnabled = intent.getBooleanExtra("SOUND_ENABLED", true);

        // SharedPreferences üzerinden kullanıcının Ayarlar'da yaptığı tercihleri okuyoruz
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        int volumePercent = prefs.getInt("ALARM_VOLUME_PERCENT", 80); // Varsayılan %80
        String ringtoneUriStr = prefs.getString("ALARM_RINGTONE_URI", ""); // Kullanıcının seçtiği özel ses

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null) return;

        // Kullanıcının ayarladığı manuel ses seviyesini telefonun alarm sesine uyguluyoruz
        if (soundEnabled) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                int maxAlarmVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
                // SeekBar'dan gelen yüzdeye göre hedef ses kademesini hesaplıyoruz
                int targetVolume = (int) (maxAlarmVolume * (volumePercent / 100.0));
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM, targetVolume, 0);
            }
        }

        // Zil sesi URI belirleme mantığı (Özel ses seçildiyse onu, yoksa varsayılanı kullan)
        Uri alarmSound;
        if (!ringtoneUriStr.isEmpty()) {
            alarmSound = Uri.parse(ringtoneUriStr);
        } else {
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmSound == null) {
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 1. Çok Yüksek Sesli Alarm Kanalı
            NotificationChannel soundChannel = new NotificationChannel(
                    CHANNEL_ID_SOUND,
                    "Yüksek Sesli İlaç Alarmları",
                    NotificationManager.IMPORTANCE_HIGH
            );
            soundChannel.enableVibration(true);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            soundChannel.setSound(alarmSound, audioAttributes);
            notificationManager.createNotificationChannel(soundChannel);

            // 2. Sadece Titreşim Kanalı
            NotificationChannel vibeChannel = new NotificationChannel(
                    CHANNEL_ID_VIBE,
                    "İlaç Sadece Titreşim",
                    NotificationManager.IMPORTANCE_HIGH
            );
            vibeChannel.enableVibration(true);
            vibeChannel.setSound(null, null);
            notificationManager.createNotificationChannel(vibeChannel);
        }

        String activeChannelId = soundEnabled ? CHANNEL_ID_SOUND : CHANNEL_ID_VIBE;

        Intent clickIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                medId,
                clickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        String title;
        String contentText;

        if (offset > 0) {
            title = "Yaklaşan İlaç: " + (name != null ? name : "");
            contentText = "İlaç saatinize " + offset + " dakika kaldı!";
        } else {
            title = "İlaç Zamanı! 💊";
            contentText = (name != null ? name : "İlaç") + " - Doz: " + (dosage != null ? dosage : "") + " içme vakti geldi!";
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, activeChannelId)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle(title)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (soundEnabled) {
            builder.setSound(alarmSound);
            builder.setVibrate(new long[]{0, 1000, 1000, 1000});
        } else {
            builder.setSound(null);
            builder.setVibrate(new long[]{0, 500, 500, 500});
        }

        int notificationId = (medId * 10) + offset;
        notificationManager.notify(notificationId, builder.build());
    }
}