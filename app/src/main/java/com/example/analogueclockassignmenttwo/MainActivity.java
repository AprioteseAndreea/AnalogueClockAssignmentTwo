package com.example.analogueclockassignmenttwo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    MySurfaceView mySurfaceView = null;
    Button alarmsBtn, settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmsBtn = findViewById(R.id.alarms);
        settingsBtn = findViewById(R.id.settings);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Analogue clock app");
        }
        alarmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), AlarmActivity.class);
                startActivity(myIntent);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), SettingsActivity.class);
                startActivity(myIntent);
            }
        });
        mySurfaceView = new MySurfaceView(this, 300);

        LinearLayout linearLayout = findViewById(R.id.surfaceView);
        linearLayout.addView(mySurfaceView);

    }

    protected void onResume() {
        super.onResume();
        mySurfaceView.onResumeMySurfaceView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySurfaceView.onPauseMySurfaceView();
    }
}
