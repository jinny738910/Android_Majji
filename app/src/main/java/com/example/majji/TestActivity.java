package com.example.majji;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    private static String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        findViewById(R.id.spaoScrap).setOnClickListener(onClickListener);
        findViewById(R.id.baonScrap).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new android.view.View.OnClickListener() {
        public void onClick(android.view.View v) {

            switch (v.getId()) {
                case R.id.spaoScrap:
                    Scrap_Spao scrap = new Scrap_Spao();
                    scrap.collectProduct();
                    break;

                case R.id.baonScrap:

            }
        }
    };
}

