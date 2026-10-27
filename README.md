# 💊 İlaç Hatırlatıcı (Medicine Reminder App)

<p align="center">
  <img src="https://private-user-images.githubusercontent.com/186428766/685963736-68f9caf7-6a51-4c64-bedc-20318fbd8238.jpeg?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3OTMwOTU3OTcsIm5iZiI6MTc5MzA5NTQ5NywicGF0aCI6Ii8xODY0Mjg3NjYvNjg1OTYzNzM2LTY4ZjljYWY3LTZhNTEtNGM2NC1iZWRjLTIwMzE4ZmJkODIzOC5qcGVnP1gtQW16LUFsZ29yaXRobT1BV1M0LUhNQUMtU0hBMjU2JlgtQW16LUNyZWRlbnRpYWw9QUtJQVZDT0RZTFNBNTNQUUs0WkElMkYyMDI2MTAyNyUyRnVzLWVhc3QtMSUyRnMzJTJGYXdzNF9yZXF1ZXN0JlgtQW16LURhdGU9MjAyNjEwMjdUMTAwNDU3WiZYLUFtei1FeHBpcmVzPTMwMCZYLUFtei1TaWduYXR1cmU9NWEzMTBhMDRiODMzMDdkOTZiNTliZGJhY2U3ZTEyNzk0ZTczZDRlZTM2M2M3NDY3OTE3ZmQyZGJlNjcxMDE1MyZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QmcmVzcG9uc2UtY29udGVudC10eXBlPWltYWdlJTJGanBlZyJ9.pcGPMKR_qIHLBTaBAg8kTgeOdLOdzDuXzoDdpi7Z_Qo" width="200px" alt="Status">
  <img src="https://img.shields.io/badge/Platform-Android-green" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Java-orange" alt="Language">
  <img src="https://img.shields.io/badge/License-MIT-yellow" alt="License">
</p>
<br>
Android platformu için geliştirilmiş, kullanıcıların ilaç saatlerini kolayca yönetmesini, özel alarm sesleri ve manuel ses seviyesi ayarlayabilmesini sağlayan modern bir hatırlatıcı uygulaması.

---

## ✨ Özellikler

- **Alarm ve Bildirim Yönetimi:** Belirlenen saatlerde tam zamanında tetiklenen hatırlatıcılar (`AlarmManager` ve `BroadcastReceiver` desteği).
- **Özel Ses Seviyesi Kontrolü:** `SeekBar` yardımıyla alarm ses düzeyini yüzde bazında (%0 - %100) manuel ayarlayabilme.
- **Kişiselleştirilebilir Ayarlar:** Modern ve kullanıcı dostu arayüz tasarımı.
- **Pil Optimizasyonu Uyumlu:** `setExactAndAllowWhileIdle` desteği ile derin uyku modunda bile güvenilir tetiklenme.

---

## 📸 Ekran Görüntüleri

*(Buraya uygulamagörsellerini veya emülatör ekran görüntülerinizi ekleyebilirsiniz)*
| Ana Ekran | Ayarlar Ekranı |
| ----------- | ----------- |
| *[Görsel Eklenecek]* | *[Görsel Eklenecek]* |

---

## 🛠️ Kullanılan Teknolojiler

Proje geliştirilirken aşağıdaki Android bileşenleri ve teknolojileri kullanılmıştır:
- **Dil:** Java
- **Arayüz:** XML Layouts (ConstraintLayout, LinearLayout)
- **Veri Saklama:** SharedPreferences (Ayarlar ve tercihler için)
- **Zamanlayıcılar:** AlarmManager & PendingIntent
- **Bildirimler:** NotificationManager & NotificationChannel (Yüksek öncelikli sesli/titreşimli kanallar)

---

## 🚀 Projeyi Çalıştırma (Kurulum)

Bu projeyi kendi bilgisayarınızda test etmek veya geliştirmek istiyorsanız:

1. Depoyu klonlayın:
   ```bash
   git clone [https://github.com/KULLANICI_ADINIZ/ilac-hatirlatici.git](https://github.com/KULLANICI_ADINIZ/ilac-hatirlatici.git)
