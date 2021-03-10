package com.shr.interview;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    SView sView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        sView =  findViewById(R.id.sview);
    }

    public void click(View view){
        sView.smartScrollTo(-100,0);
    }
}
