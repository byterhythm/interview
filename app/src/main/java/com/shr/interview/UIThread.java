package com.shr.interview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;

public class UIThread extends Thread {
    //作为主线程使用


    public UIThread() {
        Log.i("shr", "UIThread() id:" + Thread.currentThread().getId());

    }

    Handler mHandler;

    @Override
    public void run() {
        super.run();
        Looper.prepare();
       Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.i("shr", "handleMessage() id:" + Thread.currentThread().getId());
                if (msg.what == 1) {
                    Log.i("shr", msg.getData().get("shr") + "");
                }
            }
        };
        mHandler = handler;
        Looper.loop();
    }

    public void asyncTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putString("shr", "hello ");
                Log.i("shr", "thread id:" + Thread.currentThread().getId());
                Message msg = Message.obtain();
                msg.what = 1;
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        }).start();
    }
}
