package com.chengfu.recyclerviewplus.samples.headerandfooter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chengfu.recyclerviewplus.samples.R;

/**
 * Created by ChengFu on 2017/8/18.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HeaderAndFooterActivity.class);
                startActivity(intent);
            }
        });
    }
}
