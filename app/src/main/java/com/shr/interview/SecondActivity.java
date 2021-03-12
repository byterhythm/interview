package com.shr.interview;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager iBookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> list = iBookManager.getBookList();
                for (Book book : list) {
                    Log.i("shr", "book:" + book.getName());
                }
                iBookManager.addBook(new Book(" di yi hang dai ma"));
                for (Book book : iBookManager.getBookList()) {
                    Log.i("shr", "book added:" + book.getName());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        int maxSize = 4 * 1024 * 1024;
        LruCache<String, Bitmap> bitmapCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    boolean isBindConnection;

    public void sendMsg(View view) {
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        isBindConnection = true;
    }

    Intent intent;

    public void handlerThread(View view) {
        Log.i("shr", "线程" + Thread.currentThread().getId());
        intent = new Intent(this, ShrService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        if (isBindConnection) {
            unbindService(serviceConnection);
        }
        if (intent != null) {
            stopService(intent);
        }
        super.onDestroy();
    }
}
