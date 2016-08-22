package com.hochan.sortstudy;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
	public int mCurrentStep = 0;
	private int mTotalStepCount = 0;
	public List<TextView> mNumberTvs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insertion_sort);

		mUnsorted = new int[]{8, 4, 2, 9, 5, 6, 3, 1, 7};
		mUnsortedCopy = mUnsorted.clone();
		initView(mUnsorted);
		int steps = calculateSteps();
		if(steps != 0){
			mTotalStepCount = steps;
		}
	}

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
			TextView numberTv = getNumberTv(unsorted[i], width);
			numbersLl.addView(numberTv);
			mNumberTvs.add(numberTv);
			mIntToTv.put(unsorted[i], numberTv);
		}
	}

	@NonNull
	private TextView getNumberTv(int value, int width) {
		TextView numberTv = new TextView(getApplicationContext());
		numberTv.setText(String.valueOf(value));
		numberTv.setTextSize(15);
		numberTv.getPaint().setFakeBoldText(true);
		numberTv.setBackgroundResource(R.drawable.shape_number_bg);
		numberTv.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(width, width);
		textViewLp.leftMargin = 20;
		numberTv.setLayoutParams(textViewLp);
		return numberTv;
	}

	public abstract void setDescription(TextView textView);

	@Override
	public void onClick(View v) {
		if(mCurrentStep == 0){
			for(int i = 0; i < mUnsorted.length; i++){
				mNumberTvs.get(i).animate().translationX(0).translationY(0).setDuration(500).start();
				mResultLl.removeAllViews();
				mUnsorted = mUnsortedCopy.clone();
			}
			mPerformBtn.setText("下一步");
		}
		mPerformBtn.setClickable(false);
		performSort(mCurrentStep);
	}

	public abstract int calculateSteps();

	public abstract void performSort(int step);

	public void showResult() {
		LinearLayout linearLayout = new LinearLayout(getApplicationContext());
		TextView textView = new TextView(getApplicationContext());
		textView.setText(String.format("第%s轮:  ", mCurrentStep));
		textView.getPaint().setFakeBoldText(true);
		textView.setTextSize(15);
		textView.setTextColor(getResources().getColor(R.color.colorPrimary));
		linearLayout.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		for(int i = 0; i < mUnsorted.length; i++){
			TextView tmpTv = getNumberTv(mUnsorted[i], 40);
			linearLayout.addView(tmpTv);
		}

		LinearLayout.LayoutParams linearLayoutLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayoutLp.topMargin = 20;
		mResultLl.addView(linearLayout, linearLayoutLp);

		stepFinish();
	}

	public void stepFinish(){
		mPerformBtn.setClickable(true);
		if(mCurrentStep == mUnsorted.length - 1 && mTotalStepCount == 0){
			sortFinish();
		}
		if(mTotalStepCount != 0 && mCurrentStep == mTotalStepCount - 1){
			sortFinish();
		}
		mCurrentStep++;
	};

	public void sortFinish(){
		mPerformBtn.setText("重来");
		mCurrentStep = 0;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
