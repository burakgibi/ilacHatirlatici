package com.example.ilachatirlatici;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MedicinePrefs";
    private static final String KEY_MEDICINE_LIST = "MedicineList";

    private RecyclerView recyclerView;
    private MedicineAdapter adapter;
    private List<Medicine> medicineList;
    private FloatingActionButton fabAdd;
    private View layoutEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Android 13+ Bildirim İzni Kontrolü
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        101
                );
            }
        }

        // Android 12+ Hassas Alarm İzni Yönlendirmesi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }

        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);
        layoutEmpty = findViewById(R.id.layoutEmpty);

        // Hafızadaki Verileri Yükle
        loadData();

        adapter = new MedicineAdapter(medicineList, position -> deleteMedicine(position));

        checkEmptyState();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> showAddMedicineDialog());

        Button btnOpenSettings = findViewById(R.id.btnOpenSettings);
        btnOpenSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void checkEmptyState() {
        if (medicineList == null || medicineList.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void showAddMedicineDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_medicine, null);
        builder.setView(dialogView);

        Button btnSelectTime = dialogView.findViewById(R.id.btnSelectTime);
        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etDosage = dialogView.findViewById(R.id.etDosage);
        EditText etInterval = dialogView.findViewById(R.id.etInterval);
        Switch switchSound = dialogView.findViewById(R.id.switchSound); // Sesli alarm Switch'i

        AlertDialog dialog = builder.create();

        btnSelectTime.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String dosage = etDosage.getText().toString().trim();
            String intervalStr = etInterval.getText().toString().trim();

            if (name.isEmpty() || dosage.isEmpty()) {
                Toast.makeText(MainActivity.this, "Lütfen alanları doldurun!", Toast.LENGTH_SHORT).show();
                return;
            }

            int intervalHours = intervalStr.isEmpty() ? 0 : Integer.parseInt(intervalStr);
            boolean isSoundEnabled = switchSound != null && switchSound.isChecked(); // Switch durumunu alıyoruz

            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                    (view, selectedHour, selectedMinute) -> {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        selectedCalendar.set(Calendar.MINUTE, selectedMinute);
                        selectedCalendar.set(Calendar.SECOND, 0);
                        selectedCalendar.set(Calendar.MILLISECOND, 0);

                        if (selectedCalendar.getTimeInMillis() <= System.currentTimeMillis()) {
                            selectedCalendar.add(Calendar.DATE, 1);
                        }

                        int uniqueId = (int) (System.currentTimeMillis() % 10000);

                        // 6 Parametreli güncel Medicine nesnesini oluşturuyoruz (isSoundEnabled dahil)
                        Medicine newMedicine = new Medicine(
                                uniqueId,
                                name,
                                dosage,
                                intervalHours,
                                selectedCalendar.getTimeInMillis(),
                                isSoundEnabled
                        );

                        medicineList.add(newMedicine);
                        saveData();
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                        checkEmptyState();

                        // Alarmı Kur
                        AlarmScheduler.scheduleMedicineAlarm(MainActivity.this, newMedicine);

                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, name + " eklendi!", Toast.LENGTH_SHORT).show();
                    }, hour, minute, true);

            timePickerDialog.show();
        });

        dialog.show();
    }

    private void deleteMedicine(int position) {
        Medicine removedMedicine = medicineList.get(position);

        AlarmScheduler.cancelMedicineAlarm(MainActivity.this, removedMedicine);

        medicineList.remove(position);
        adapter.notifyItemRemoved(position);

        saveData();
        checkEmptyState();

        Toast.makeText(this, removedMedicine.getName() + " silindi.", Toast.LENGTH_SHORT).show();
    }

    // HAFIZA METOTLARI
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(medicineList);
        editor.putString(KEY_MEDICINE_LIST, json);
        editor.apply();

        checkEmptyState();
    }

    private void loadData() {
        checkEmptyState();

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_MEDICINE_LIST, null);
        Type type = new TypeToken<ArrayList<Medicine>>() {}.getType();

        medicineList = gson.fromJson(json, type);

        if (medicineList == null) {
            medicineList = new ArrayList<>();
        }
    }
}