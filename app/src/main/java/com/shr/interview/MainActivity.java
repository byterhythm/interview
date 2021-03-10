package com.shr.interview;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TextView tv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("MainActivity.onCreate ");

        tv_view = findViewById(R.id.tv_view);


    }

    public void move(View view) {
        ObjectAnimator.ofFloat(tv_view, "translationX", 0, 100).setDuration(1000).start();
        startActivity(new Intent(this, SecondActivity.class));

    }
}
