package com.example.task4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.task4.ui.custom.CustomCircleView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Button button = findViewById(R.id.goToMapButton);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        intentGoToMapActivity();
    }

    private void intentGoToMapActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}