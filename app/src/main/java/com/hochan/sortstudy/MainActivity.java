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

		INSERTION_SORT("插入排序", InsertionSortActivity.class),
		BUBBLE_SORT("冒泡排序", BubbleSortActivity.class),
		SELECTION_SORT("选择排序", SelectionSortActivity.class);

		String mName;
		Class<? extends Activity> mActivityClass;

		SORT_METHOD(String name, Class<? extends Activity> activityClass){
			this.mName = name;
			this.mActivityClass = activityClass;
		}

		void launch(Activity activity){
			activity.startActivity(new Intent(activity, mActivityClass));
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
