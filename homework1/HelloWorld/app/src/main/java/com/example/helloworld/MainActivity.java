package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button;
    private Button button2;
    private Button addButton;
    private TextView textView;
    private TextView addAns;
    private EditText addNum1;
    private EditText addNum2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        addButton = findViewById(R.id.button3);
        textView = findViewById(R.id.textView);
        addAns = findViewById(R.id.textView2);
        addNum1 = findViewById(R.id.editText1);
        addNum2 = findViewById(R.id.editText2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick");
                textView.setText(R.string.app_click_text1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick");
                textView.setText(R.string.app_click_text2);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick");
                String temp = addNum1.getText().toString();
                int num1;
                try {
                    num1 = Integer.parseInt(addNum1.getText().toString());
                } catch (Exception e) {
                    num1 = 0;
                }
                Log.d(TAG, "num1 get " + String.valueOf(num1));
                String temp2 = addNum1.getText().toString();
                int num2;
                try {
                    num2 = Integer.parseInt(addNum2.getText().toString());
                } catch (Exception e) {
                    num2 = 0;
                }
                int num3 = num1 + num2;
                Log.d(TAG, "Answer is" + String.valueOf(num3));
                addAns.setText(String.valueOf(num3));
            }
        });
    }
}
