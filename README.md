# 💊 İlaç Hatırlatıcı (Medicine Reminder App)

<p align="center">
  <img src="https://img.shields.io/badge/Status-Beta%200.3.2-blue" alt="Status">
  <img src="https://img.shields.io/badge/Platform-Android-green" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Java-orange" alt="Language">
  <img src="https://img.shields.io/badge/License-MIT-yellow" alt="License">
</p>

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
