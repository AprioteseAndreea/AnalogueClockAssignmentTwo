package com.example.analogueclockassignmenttwo;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import top.defaults.colorpicker.ColorPickerPopup;

public class SettingsActivity extends AppCompatActivity {

    private Button setHourColorButton, setMinutesColorButton, setSecondsColorButton, applyChangesButton;

    private View hourColorPreview, minutesColorPreview, secondsColorPreview;

    private int mDefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Settings");
        }
        SharedPreferences sp = null;
        if (getSharedPreferences("myPref", MODE_PRIVATE) != null) {
            sp = getSharedPreferences("myPref", MODE_PRIVATE);
        }
        final SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();

        setHourColorButton = findViewById(R.id.set_color_hour_button);
        setMinutesColorButton = findViewById(R.id.set_color_button_minutes);
        setSecondsColorButton = findViewById(R.id.set_color_seconds_button);
        applyChangesButton = findViewById(R.id.apply_changes);

        hourColorPreview = findViewById(R.id.preview_selected_color_hour);
        minutesColorPreview = findViewById(R.id.preview_selected_color_minutes);
        secondsColorPreview = findViewById(R.id.preview_selected_color_seconds);

        if (sp != null) {
            try {
                hourColorPreview.setBackgroundColor(Color.parseColor(sp.getString("hourColor", "#303F9F")));

            } catch (IllegalArgumentException e) {
                hourColorPreview.setBackgroundColor(Integer.parseInt(sp.getString("hourColor", "#303F9F")));
            }

            try {
                minutesColorPreview.setBackgroundColor(Color.parseColor(sp.getString("minutesColor", "#FF4081")));

            } catch (IllegalArgumentException e) {
                minutesColorPreview.setBackgroundColor(Integer.parseInt(sp.getString("minutesColor", "#FF4081")));
            }
            try {
                secondsColorPreview.setBackgroundColor(Color.parseColor(sp.getString("secondsColor", "#5D6CBC")));

            } catch (IllegalArgumentException e) {
                secondsColorPreview.setBackgroundColor(Integer.parseInt(sp.getString("secondsColor", "#5D6CBC")));
            }
        }

        applyChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setHourColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerPopup.Builder(SettingsActivity.this).initialColor(
                        Color.RED).enableBrightness(true)
                        .enableAlpha(true)
                        .okTitle("Choose")
                        .cancelTitle("Cancel")
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(v,
                                new ColorPickerPopup.ColorPickerObserver() {
                                    @Override
                                    public void
                                    onColorPicked(int color) {
                                        mDefaultColor = color;
                                        hourColorPreview.setBackgroundColor(mDefaultColor);
                                        editor.putString("hourColor", String.valueOf(mDefaultColor));
                                        editor.apply();

                                    }
                                });
            }
        });
        setMinutesColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerPopup.Builder(SettingsActivity.this).initialColor(
                        Color.RED).enableBrightness(true)
                        .enableAlpha(true)
                        .okTitle("Choose")
                        .cancelTitle("Cancel")
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(v,
                                new ColorPickerPopup.ColorPickerObserver() {
                                    @Override
                                    public void
                                    onColorPicked(int color) {
                                        mDefaultColor = color;
                                        minutesColorPreview.setBackgroundColor(mDefaultColor);
                                        editor.putString("minutesColor", String.valueOf(mDefaultColor));
                                        editor.apply();

                                    }
                                });
            }
        });

        setSecondsColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerPopup.Builder(SettingsActivity.this).initialColor(
                        Color.RED).enableBrightness(true)
                        .enableAlpha(true)
                        .okTitle("Choose")
                        .cancelTitle("Cancel")
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(v,
                                new ColorPickerPopup.ColorPickerObserver() {
                                    @Override
                                    public void
                                    onColorPicked(int color) {
                                        mDefaultColor = color;
                                        secondsColorPreview.setBackgroundColor(mDefaultColor);
                                        editor.putString("secondsColor", String.valueOf(mDefaultColor));
                                        editor.apply();
                                    }
                                });
            }
        });

    }
}