package com.example.ilachatirlatici;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private SeekBar seekBarVolume;
    private TextView tvVolumeLabel;
    private Button btnSaveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // XML'deki bileşenleri bağlıyoruz
        seekBarVolume = findViewById(R.id.seekBarVolume);
        tvVolumeLabel = findViewById(R.id.tvVolumeLabel);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);

        // SharedPreferences'ten daha önce kaydedilmiş ses yüzdesini alıyoruz (Varsayılan: 50)
        SharedPreferences prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        int savedVolume = prefs.getInt("ALARM_VOLUME_PERCENT", 50);

        // Arayüzü kaydedilen değerle güncelliyoruz
        seekBarVolume.setProgress(savedVolume);
        tvVolumeLabel.setText("Ses Seviyesi: %" + savedVolume);

        // SeekBar kaydırıldıkça yüzde yazısını anlık değiştirelim
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvVolumeLabel.setText("Ses Seviyesi: %" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // "Ayarları Kaydet" butonuna tıklandığında
        btnSaveSettings.setOnClickListener(v -> {
            int currentVolume = seekBarVolume.getProgress();

            // Değeri hafızaya kaydediyoruz
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("ALARM_VOLUME_PERCENT", currentVolume);
            editor.apply();

            Toast.makeText(SettingsActivity.this, "Ayarlar kaydedildi!", Toast.LENGTH_SHORT).show();
            finish(); // Ekranı kapatıp ana ekrana dön
        });
    }
}