package com.shr.interview;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerService extends Service {

    CopyOnWriteArrayList<Book> books = new CopyOnWriteArrayList<>();

    private Binder binder = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            books.add(book);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        books.add(new Book("suan fa"));
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
