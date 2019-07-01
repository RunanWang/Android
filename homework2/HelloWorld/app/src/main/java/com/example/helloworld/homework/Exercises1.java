package com.example.helloworld.homework;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * 作业1：
 * 打印出Activity屏幕切换 Activity会执行什么生命周期？
 * 2019-07-01 15:30:49.642 10300-10300/com.example.helloworld D/Exercises1-Life: onPause
 * 2019-07-01 15:30:49.643 10300-10300/com.example.helloworld D/Exercises1-Life: onStop
 * 2019-07-01 15:30:49.643 10300-10300/com.example.helloworld D/Exercises1-Life: onDestroy
 * 2019-07-01 15:30:49.704 10300-10300/com.example.helloworld D/Exercises1-Life: onCreate
 * 2019-07-01 15:30:49.733 10300-10300/com.example.helloworld D/Exercises1-Life: onStart
 * 2019-07-01 15:30:49.750 10300-10300/com.example.helloworld D/Exercises1-Life: onResume
 * 可见先把Activity摧毁了然后又重新建立了
 *
 */
public class Exercises1 extends AppCompatActivity {
    private static final String TAG = "Exercises1-Life";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
}
