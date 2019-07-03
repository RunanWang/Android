package com.bytedance.clockapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bytedance.clockapplication.widget.Clock;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "debugR";
    private View mRootView;
    private Clock mClockView;
    private RenewHandlerThread myHandler;
    //private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootView = findViewById(R.id.root);
        mClockView = findViewById(R.id.clock);


        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClockView.setShowAnalog(!mClockView.isShowAnalog());
            }
        });
    }

    @Override
    protected void onStart() {
        myHandler = new RenewHandlerThread("name", mClockView);
        Log.d(TAG, "onStart() called");
        myHandler.start();
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop() called");
        myHandler.interrupt();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        super.onDestroy();
    }
}
