package com.example.ilachatirlatici;

public class Medicine {
    private int id;
    private String name;
    private String dosage;
    private int intervalHours;
    private long timeInMillis;
    private boolean soundEnabled; // true: Ses + Titreşim, false: Sadece Titreşim

    // 1. Yeni ve tüm özellikleri içeren Constructor (6 Parametreli)
    public Medicine(int id, String name, String dosage, int intervalHours, long timeInMillis, boolean soundEnabled) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.intervalHours = intervalHours;
        this.timeInMillis = timeInMillis;
        this.soundEnabled = soundEnabled;
    }

    // 2. Geriye dönük uyumluluk için eski Constructor (5 Parametreli - Varsayılan ses açık)
    public Medicine(int id, String name, String dosage, int intervalHours, long timeInMillis) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.intervalHours = intervalHours;
        this.timeInMillis = timeInMillis;
        this.soundEnabled = true; // Varsayılan olarak sesli
    }

    // Getter ve Setter Metotları
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDosage() {
        return dosage;
    }

    public int getIntervalHours() {
        return intervalHours;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }
}