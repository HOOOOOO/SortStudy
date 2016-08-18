package com.hochan.sortstudy;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by zhendong_chen on 2016/8/18.
 */
public abstract class BaseSortActiviy extends AppCompatActivity implements View.OnClickListener{

	private Button mPerformBtn;
	private LinearLayout mResultLl;
	public int[] mUnsorted;
	private int[] mUnsortedCopy;
	public Map<Integer, TextView> mIntToTv;
	public int mTranslationY;
	public int mTranslationX;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insertion_sort);

		mUnsorted = new int[]{4, 2, 5, 6, 3, 1};
		mUnsortedCopy = mUnsorted.clone();
		initView(mUnsorted);
	}

	private List<TextView> mNumberTvs;

	private void initView(int[] unsorted) {
		LinearLayout numbersLl = (LinearLayout) findViewById(R.id.numbers_ll);
		mPerformBtn = (Button) findViewById(R.id.perform_btn);
		mPerformBtn.setOnClickListener(this);
		mResultLl = (LinearLayout) findViewById(R.id.result_ll);
		TextView discriptionTv = (TextView) findViewById(R.id.description_tv);
		setDescription(discriptionTv);

		int width = (ScreenTools.getScreenWidth(getApplicationContext()) - ScreenTools.dip2px(getApplicationContext(), 16) * 2
				- (unsorted.length + 1) * 20) / unsorted.length;
		mTranslationY = width + 20;
		mTranslationX = width + 20;
		mNumberTvs = new ArrayList<>(unsorted.length);
		mIntToTv = new HashMap<>();
		for(int i = 0; i < unsorted.length; i++){
			TextView numberTv = new TextView(getApplicationContext());
			numberTv.setText(String.valueOf(unsorted[i]));
			numberTv.setTextSize(15);
			numberTv.getPaint().setFakeBoldText(true);
			numberTv.setBackgroundResource(R.drawable.shape_number_bg);
			numberTv.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(width, width);
			textViewLp.leftMargin = 20;
			numbersLl.addView(numberTv, textViewLp);
			mNumberTvs.add(numberTv);
			mIntToTv.put(unsorted[i], numberTv);
		}
	}

	public abstract void setDescription(TextView textView);

	public int mStep = 0;

	@Override
	public void onClick(View v) {
		if(mStep == 0){
			for(int i = 0; i < mUnsorted.length; i++){
				mNumberTvs.get(i).animate().translationX(0).translationY(0).setDuration(500).start();
				mResultLl.removeAllViews();
				mUnsorted = mUnsortedCopy.clone();
			}
			mPerformBtn.setText("下一步");
		}
		mStep++;
		mPerformBtn.setClickable(false);
		performSort(mStep);
	}

	public abstract void performSort(int step);

	public void showResult() {
		LinearLayout linearLayout = new LinearLayout(getApplicationContext());
		TextView textView = new TextView(getApplicationContext());
		textView.setText(String.format("第%s轮:  ", mStep));
		textView.getPaint().setFakeBoldText(true);
		textView.setTextSize(15);
		textView.setTextColor(getResources().getColor(R.color.colorPrimary));
		linearLayout.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		for(int i = 0; i < mUnsorted.length; i++){
			TextView tmpTv = new TextView(getApplicationContext());
			tmpTv.setText(String.valueOf(mUnsorted[i]));
			tmpTv.setTextSize(15);
			tmpTv.setGravity(Gravity.CENTER);
			tmpTv.getPaint().setFakeBoldText(true);
			tmpTv.setBackgroundResource(R.drawable.shape_number_bg);
			LinearLayout.LayoutParams tmpTvLp = new LinearLayout.LayoutParams(40, 40);
			tmpTvLp.leftMargin = 20;
			linearLayout.addView(tmpTv, tmpTvLp);
		}

		LinearLayout.LayoutParams linearLayoutLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayoutLp.topMargin = 20;
		mResultLl.addView(linearLayout, linearLayoutLp);

		mPerformBtn.setClickable(true);
		if(mStep == mUnsorted.length - 1){
			mPerformBtn.setText("重来");
			mStep = 0;
		}
	}
}
