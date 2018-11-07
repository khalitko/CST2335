package com.example.khalitko.androidlabs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class WeatherForecast extends Activity {

    private ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        pBar = findViewById(R.id.wather_progressBar);

            pBar.setVisibility(View.VISIBLE);
            pBar.setProgress(pBar.getProgress() + i);
            return null;
        });
    }
}
