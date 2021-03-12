package com.shr.interview;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class IService extends Service {

    public static final int CLIENT = 100;

    Messenger messenger = new Messenger(new IServiceHandler());

    private static class IServiceHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int tag = msg.what;
            switch (tag) {
                case CLIENT:
                    Log.i("shr", "收到客户端消息" + msg.getData().getString("msg"));
                    Messenger messenger = msg.replyTo;
                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    message.what = 101;
                    bundle.putString("server", "i am from server");
                    message.setData(bundle);
                    try {
                        messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
