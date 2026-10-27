package com.example.ilachatirlatici;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlarmScheduler {

    public static void scheduleMedicineAlarm(Context context, Medicine medicine) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        int[] offsetsInMinutes = {5, 2, 1, 0};

        for (int offset : offsetsInMinutes) {
            // Çarpı işareti ve zaman hesaplaması düzeltildi
            long targetTime = medicine.getTimeInMillis() - (offset * 60 * 1000L);

            if (targetTime <= System.currentTimeMillis()) {
                continue;
            }

            Intent intent = new Intent(context, AlarmReceiver.class);
            // HER OFFSET İÇİN BENZERSİZ ACTION (Ekstraların karışmasını önler)
            intent.setAction("ACTION_MED_ALARM_" + medicine.getId() + "_" + offset);

            intent.putExtra("MED_ID", medicine.getId());
            intent.putExtra("MED_NAME", medicine.getName());
            intent.putExtra("MED_DOSAGE", medicine.getDosage());
            intent.putExtra("TIME_OFFSET", offset);

            // İŞTE BU EKSİKTİ: Switch'in durumunu AlarmReceiver'a aktarıyoruz
            intent.putExtra("SOUND_ENABLED", medicine.isSoundEnabled());

            int uniquePendingIntentId = (medicine.getId() * 10) + offset;

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    uniquePendingIntentId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            if (alarmManager != null) {
                try {
                    alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            targetTime,
                            pendingIntent
                    );
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void cancelMedicineAlarm(Context context, Medicine medicine) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int[] offsetsInMinutes = {5, 2, 1, 0};

        for (int offset : offsetsInMinutes) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.setAction("ACTION_MED_ALARM_" + medicine.getId() + "_" + offset);

            int uniquePendingIntentId = (medicine.getId() * 10) + offset;

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    uniquePendingIntentId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
            }
        }
    }
}