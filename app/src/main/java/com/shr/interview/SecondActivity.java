package com.shr.interview;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
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
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.tv_update);
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
        intent.putExtra("url", "http:www.google.com");
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

    public void CancelAsyncTask(View view) {
        if (downloadTask != null && downloadTask.getStatus() == AsyncTask.Status.RUNNING) {
            downloadTask.cancel(false);
        }
    }

    static class DownloadTask extends AsyncTask<String, Integer, String> {

        private final WeakReference<Activity> weakReference;
        private static final int MAX_SIZE = 100;

        DownloadTask(Activity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(String... strings) {

            String flag = "start";
            //模拟下载
            try {
                int progress = 0;
                while (progress < MAX_SIZE) {
                    if (isCancelled())
                        return "cancel";
                    Thread.sleep(1000);
                    progress += 5;
                    publishProgress(progress);
                    Log.i("shr", "progress=" + progress);
                }
                flag = "end";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return flag;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("shr", "onProgressUpdate=" + values[0]);
            if (weakReference.get() != null) {
                SecondActivity secondActivity = (SecondActivity) weakReference.get();
                if (secondActivity.textView != null) {
                    secondActivity.textView.setText(values[0] + "");
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (weakReference.get() != null) {
                SecondActivity secondActivity = (SecondActivity) weakReference.get();
                secondActivity.textView.setText(s);
            }
        }
    }

    DownloadTask downloadTask;


    public void AsyncTask(View view) {
        downloadTask = new DownloadTask(this);
        downloadTask.execute("http://www.google.com");
    }
}
