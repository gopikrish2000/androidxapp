package com.mlrecommendation.gopi.androidxsamplearchitectureapp.sharedPrefsPerformance;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R;

public class SharedPreferenceTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference_test);

        findViewById(R.id.prefWriteBtn).setOnClickListener((view) -> {
            int i = 0;
            final long time = System.currentTimeMillis();
            while (i++ < 100) {
                final SharedPreferences sharedPreferencesFile = this.getSharedPreferences("abc"+i, 0);
                sharedPreferencesFile.edit().putString("MyKey"+i, "GopiVal"+i).apply();
            }
            System.out.println("Write done in time " + (System.currentTimeMillis() - time));
        });

        findViewById(R.id.prefReadBtn).setOnClickListener((view) -> {
            int i = 0;
            final long time = System.currentTimeMillis();
            while (i++ < 100) {
                final SharedPreferences sharedPreferencesFile = this.getSharedPreferences("abc"+i, 0);
                final String val = sharedPreferencesFile.getString("MyKey"+i, "DefaultVal");
            }
            System.out.println("Read Pref = "  + " in time " + (System.currentTimeMillis() - time));
        });
    }
}
