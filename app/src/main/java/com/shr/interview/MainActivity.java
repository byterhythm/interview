package com.shr.interview;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.*;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    TextView tv_view;
    @Inject
    DownloadManager downloadManager;

    private Messenger replyMessenger = new Messenger(new ClientHandler());

    Messenger messenger;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            Message msg = Message.obtain(null,100);
            Bundle bundle = new Bundle();
            bundle.putString("msg","hello, i am from client.");
            msg.setData(bundle);
            msg.replyTo = replyMessenger;
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private static class  ClientHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 101:
                    Log.i("shr", "收到服务端消息" + msg.getData().getString("server"));
                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerMainActivityComponent.builder().build().inject(this);


        TextView tvChannel = findViewById(R.id.tv_channel);
        try {
            ApplicationInfo activityInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            String channelName = activityInfo.metaData.getString("CHANNEL");
            getCacheDir();
            tvChannel.setText("getCacheDir():" + getCacheDir() + "\n" + "getFilesDir():" + getFilesDir() + "\n"
                    + "getExternalCacheDir():" + getExternalCacheDir() + "\n"
                    + "Environment.getDownloadCacheDirectory():" + Environment.getDownloadCacheDirectory() + "\n"
                    + "Environment.getExternalStorageDirectory():" + Environment.getExternalStorageDirectory()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, IService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }


    private static String getStoragePath(Context mContext, boolean is_removale) {
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        String path = "";
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return path;

    }

    public void fixed(View view) {
        //热补丁修复
//        FixDexUtils.loadFixedDex(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
//        downloadManager.download("xxx");
//        downloadManager.download("xxx");
        startActivity(new Intent(this, SecondActivity.class));
    }

    public void showToast(View view) {
        SimpleHotFixBugTest test = new SimpleHotFixBugTest();
        test.getBug(this);

    }

    public void move(View view) {
        ObjectAnimator.ofFloat(tv_view, "translationX", 0, 100).setDuration(1000).start();
        startActivity(new Intent(this, SecondActivity.class));
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
