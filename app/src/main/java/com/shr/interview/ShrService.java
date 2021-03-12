package com.shr.interview;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.Nullable;


public class ShrService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public ShrService() {
        super("worker thread");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i("shr", "值：" + Thread.currentThread().getId());
        //模拟下载
        int progress = 0;
        try {
            while (progress < 100) {
                setProgress(progress);
                Thread.sleep(500);
                progress += 5;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("shr", "结束");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("shr", "退出");
    }

    private void setProgress(int progress) {
        Log.i("shr", "进度：" + progress);
    }
}
