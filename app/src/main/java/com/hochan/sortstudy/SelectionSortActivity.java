package com.hochan.sortstudy;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class SelectionSortActivity extends BaseSortActiviy {

	static void selection_sort(int[] unsorted) {
		for (int i = 0; i < unsorted.length; i++) {
			int min = i;
			for (int j = i; i < unsorted.length; j++) {
				if (unsorted[min] > unsorted[j]) {
					min = j;
				}
			}
			if (min != i) {
				int tmp = unsorted[min];
				unsorted[min] = unsorted[i];
				unsorted[i] = tmp;
			}
		}
	}

	@Override
	public void setDescription(TextView textView) {
		textView.setText(R.string.des_selection_sort);
	}

	@Override
	public int calculateSteps() {
		return 0;
	}

	int mI = -1;
	int mMinIndex;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void performSort(int step) {
		mI++;
		if(mI >= mUnsorted.length){
			mI = 0;
		}
		mMinIndex = mI;
		TextView tvI = mIntToTv.get(mUnsorted[mI]);
		tvI.animate().translationY(mTranslationY).scaleX(0.6f).scaleY(0.6f).setDuration(500).withEndAction(new Runnable() {
			@Override
			public void run() {
				performCompare(mI + 1);
			}
		}).start();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void performCompare(final int j) {
		if (j >= mUnsorted.length) {
			TextView tvMin = mIntToTv.get(mUnsorted[mMinIndex]);
			tvMin.animate().scaleX(1).scaleY(1).translationY(0).translationX((mI - mMinIndex) * mTranslationX + tvMin.getTranslationX()).setDuration(500).start();
			TextView tvI = mIntToTv.get(mUnsorted[mI]);
			tvI.animate().translationY(0).translationX(-(mI - mMinIndex) * mTranslationX + tvI.getTranslationX()).setDuration(500).withEndAction(new Runnable() {
				@Override
				public void run() {
					int tmp = mUnsorted[mI];
					mUnsorted[mI] = mUnsorted[mMinIndex];
					mUnsorted[mMinIndex] = tmp;
					showResult();
				}
			}).start();
			return;
		}
		final TextView tvJ = mIntToTv.get(mUnsorted[j]);
		tvJ.animate().translationY(mTranslationY).setDuration(500).withEndAction(new Runnable() {
			@Override
			public void run() {
				if (mUnsorted[j] < mUnsorted[mMinIndex]) {
					tvJ.animate().scaleX(0.6f).scaleY(0.6f).setDuration(500).start();
					TextView tvTmp = mIntToTv.get(mUnsorted[mMinIndex]);
					if (mMinIndex != mI) {
						tvTmp.animate().translationY(0).setDuration(500).start();
					}
					tvTmp.animate().scaleY(1).scaleX(1).setDuration(500).withEndAction(new Runnable() {
						@Override
						public void run() {
							mMinIndex = j;
							performCompare(j + 1);
						}
					}).start();
				} else {
					tvJ.animate().translationY(0).setDuration(500).withEndAction(new Runnable() {
						@Override
						public void run() {
							performCompare(j + 1);
						}
					}).start();
				}
			}
		}).start();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		MainActivity.SORT_METHOD.SELECTION_SORT.showCode(this);
		return true;
	}
}
