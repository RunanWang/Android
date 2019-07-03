package com.bytedance.clockapplication;

import android.os.HandlerThread;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bytedance.clockapplication.widget.Clock;

public class RenewHandlerThread extends HandlerThread implements Handler.Callback {
    public static final int MSG_RENEW = 100;
    private Handler mHandler;
    private Clock mClock;
    private static final String TAG = "debugR";

    public RenewHandlerThread(String name, Clock mClock) {
        super(name);
        this.mClock = mClock;
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler(getLooper(), this);
        mHandler.sendEmptyMessage(MSG_RENEW);
        //super.onLooperPrepared();
    }

    @Override
    public boolean handleMessage(Message message) {
        if (!Thread.currentThread().isInterrupted()) {
            switch (message.what) {
                case MSG_RENEW:
//                    mClock.setShowAnalog(mClock.isShowAnalog());
                    mClock.postInvalidate();
                    mHandler.sendEmptyMessageDelayed(MSG_RENEW, 1000);
            }
        }
        Log.d(TAG, "handleMessage() called with: message = [" + message + "]");
        return true;
    }

    @Override
    public void run() {
        if (Thread.currentThread().isInterrupted()) {
            Log.d(TAG, "run() is finished!");
            return;
        }
        Log.d(TAG, "run() will still run!");
        super.run();
    }
}
