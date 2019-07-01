package com.example.helloworld.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class JumpActivity extends AppCompatActivity {
    private static final String TAG = "JumpActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);
        int position = getIntent().getIntExtra("position", 1000);
        TextView textView = findViewById(R.id.clickNo);
        textView.setText("你点击了第" + position + "个对话");
    }
}
