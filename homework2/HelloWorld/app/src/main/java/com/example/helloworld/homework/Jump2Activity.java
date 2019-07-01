package com.example.helloworld.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Jump2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump2);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int num = getIntent().getIntExtra("position", 1);
                String ans = "";
                switch (num) {
                    case 1:
                        ans = "评论";
                        break;
                    case 2:
                        ans = "我的";
                        break;
                    case 3:
                        ans = "赞";
                        break;
                    case 4:
                        ans = "粉丝";
                        break;
                }
                ans = "您在上层点击了" + ans;
                Toast.makeText(Jump2Activity.this, ans, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
