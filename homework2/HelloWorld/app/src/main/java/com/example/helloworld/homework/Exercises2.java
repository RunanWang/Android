package com.example.helloworld.homework;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 作业2：一个抖音笔试题：统计页面所有view的个数
 * Tips：ViewGroup有两个API
 * {@link android.view.ViewGroup #getChildAt(int) #getChildCount()}
 * 用一个TextView展示出来
 */
public class Exercises2 extends AppCompatActivity {
    private static final String TAG = "Exercises2-debug";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: " + getViewCount(this.getWindow().getDecorView()));
        //Toast.makeText(getApplicationContext(), getViewCount(this.getWindow().getDecorView())+1, Toast.LENGTH_SHORT).show();
        //extView txt = findViewById(R.id.textView);
        //txt.setText(getViewCount(this.getWindow().getDecorView()));
    }

    public static int getViewCount(View view) {
        //todo 补全你的代码
        if (!(view instanceof ViewGroup)) {
            return 1;
        }
        Log.d(TAG, "getViewCount: " + view);
        int viewNum = ((ViewGroup) view).getChildCount();
        Log.d(TAG, "getViewCount: ViewNum " + viewNum);
        int childViewNum = 0;
        for (int i = 0; i < viewNum; i++) {
            View toDo = ((ViewGroup) view).getChildAt(i);
            Log.d(TAG, "getViewCount: toDo " + toDo);
            if (toDo instanceof ViewGroup) {
                childViewNum = childViewNum + getViewCount(toDo) - 1;
            }
        }
        Log.d(TAG, "getViewCount: childViewNum " + childViewNum);
        Log.d(TAG, "getViewCount: ViewNum " + viewNum);
        return viewNum + childViewNum + 1;
    }
}
