package com.hochan.sortstudy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class CodeActivity extends AppCompatActivity {

    public static final String SOTRMETHOD = "sort_method";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView ivCode = (ImageView) findViewById(R.id.iv_code);
        ivCode.setImageResource(getIntent().getIntExtra(SOTRMETHOD, 0));

    }

}
