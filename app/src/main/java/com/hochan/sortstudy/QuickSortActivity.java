package com.hochan.sortstudy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class QuickSortActivity extends AppCompatActivity {

    static void quick_sort(int unsored[], int l, int r){
        if (l < r){
            int i = l;
            int j = r;
            int x = unsored[l];

            // 所有比x小的数都在它左边 比它大的数都在它右边
            while (i < j) {
                // 从右往左找第一个比它小的数
                while (i < j && unsored[j] >= x) {
                    j--;
                }
                if (i < j) {
                    unsored[i] = unsored[j];
                    i++;
                }

                // 接着从左往右找第一个大于或等于她数
                while (i < j && unsored[i] < x) {
                    i++;
                }
                if (i < j) {
                    unsored[j] = unsored[i];
                    j--;
                }
            }
            unsored[i] = x;
            quick_sort(unsored, l, i-1);
            quick_sort(unsored, i+1, r);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_sort);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
