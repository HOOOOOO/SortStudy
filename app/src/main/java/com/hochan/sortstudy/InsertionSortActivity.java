package com.hochan.sortstudy;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class InsertionSortActivity extends BaseSortActiviy{
	/*
    插入排序的原理：始终定义第一个元素为有序的，将元素逐个插入到有序排列之中，其特点是要不断的移动数据，
	空出一个适当的位置，最后把待插入的元素放到里面去。
    插入排序的代码如下：
	static void insertion_sort(int[] unsorted)
	{
		for (int i = 1; i < unsorted.length; i++)
		{
			if (unsorted[i - 1] > unsorted[i])
			{
				int temp = unsorted[i];
				int j = i - 1;
				while (j >= 0 && unsorted[j] > temp)
				{
					unsorted[j + 1] = unsorted[j];
					j--;
				}
				unsorted[j] = temp;
			}
		}
	}*/

	@Override
	public void setDescription(TextView textView) {
		textView.setText(R.string.des_insertion_sort);
	}

	@Override
	public int calculateSteps() {
		return 0;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void performSort(int step) {
		final int i = step + 1;
		final int tmp = mUnsorted[i];
		final TextView numberTvI = mIntToTv.get(tmp);
		int translationY = numberTvI.getMeasuredHeight() + 20;
		numberTvI.animate().translationY(translationY).setDuration(500).withEndAction(new Runnable() {
			@Override
			public void run() {
				performCompare(i, i - 1, tmp, numberTvI);
			}
		}).start();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void performCompare(final int i , final int j, final int tmp, final TextView numberTvI){
		final int translationX = -(numberTvI.getMeasuredWidth() + 20);
		numberTvI.animate().translationX(translationX * (i - j)).withEndAction(new Runnable() {
			@Override
			public void run() {
				if(mUnsorted[j] > tmp){
					final TextView numberTvJ = mIntToTv.get(mUnsorted[j]);
					numberTvI.animate().translationX(translationX * (i - j)).withEndAction(new Runnable() {
						@Override
						public void run() {
							numberTvJ.animate().translationX(numberTvJ.getTranslationX()-translationX).withEndAction(new Runnable() {
								@Override
								public void run() {
									mUnsorted[j + 1] = mUnsorted[j];
									int tmpJ = j - 1;
									if(tmpJ >= 0){
										performCompare(i, tmpJ, tmp, numberTvI);
									}else{
										mUnsorted[j] = tmp;
										reset(false, numberTvI);
									}
								}
							}).setDuration(500).start();
						}
					}).setDuration(500).start();
				}else{
					mUnsorted[j + 1] = tmp;
					reset(true, numberTvI);
				}
			}
		}).setDuration(500).start();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void reset(boolean back, final TextView textView){
		if(back){
			final int translationX = textView.getMeasuredWidth() + 20;
			textView.animate().translationX(textView.getTranslationX() + translationX).setDuration(500).start();
		}
		textView.animate().translationY(0).setDuration(500).withEndAction(new Runnable() {
			@Override
			public void run() {
				showResult();
			}
		}).start();
	}
}
