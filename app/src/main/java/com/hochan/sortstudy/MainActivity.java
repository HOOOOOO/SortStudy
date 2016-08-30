package com.hochan.sortstudy;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

	enum SORT_METHOD{

		BUBBLE_SORT("冒泡排序(稳定)", BubbleSortActivity.class, R.drawable.bubble_sort),
		INSERTION_SORT("插入排序(稳定)", InsertionSortActivity.class, R.drawable.insertion_sort),
		MERGE_SORT("归并排序(稳定)", MergeSortActivity.class, R.drawable.merge_sort),
        SELECTION_SORT("选择排序", SelectionSortActivity.class, R.drawable.selection_sort);

		String mName;
		Class<? extends Activity> mActivityClass;
        int mDrawableId;

		SORT_METHOD(String name, Class<? extends Activity> activityClass, int drawableid){
			this.mName = name;
			this.mActivityClass = activityClass;
            this.mDrawableId = drawableid;
		}

		void launch(Activity activity){
			activity.startActivity(new Intent(activity, mActivityClass));
		}

        void showCode(Activity activity){
            Intent intent = new Intent(activity, CodeActivity.class);
            intent.putExtra(CodeActivity.SOTRMETHOD, mDrawableId);
            activity.startActivity(intent);
        }
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		LinearLayout sortMethodLl = (LinearLayout) findViewById(R.id.sort_method_ll);
		SORT_METHOD[] sort_methods = SORT_METHOD.values();
		for(int i = 0; i < sort_methods.length; i++){
			Button button = new Button(getApplicationContext());
			button.setTextSize(15);
			button.getPaint().setFakeBoldText(true);
			button.setText(sort_methods[i].mName);
			button.setId(i);
			button.setPadding(0, 40, 0, 40);
			LinearLayout.LayoutParams buttonLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			button.setOnClickListener(this);
			sortMethodLl.addView(button, buttonLp);
		}
	}

	@Override
	public void onClick(View v) {
		SORT_METHOD.values()[v.getId()].launch(this);
	}
}
