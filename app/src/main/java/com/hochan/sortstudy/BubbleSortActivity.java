package com.hochan.sortstudy;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.TextView;

public class BubbleSortActivity extends BaseSortActiviy {

	static void bubble_sort(int[] unsorted) {
		for (int i = 0; i < unsorted.length; i++) {
			for (int j = 0; j < unsorted.length - i - 1; j++) {
				if (unsorted[j] > unsorted[j + 1]) {
					int temp = unsorted[j];
					unsorted[j] = unsorted[j + 1];
					unsorted[j + 1] = temp;
				}
			}
		}
	}

	int mJ = 0;
	int mCurrentI = 0;

	@Override
	public void setDescription(TextView textView) {
		textView.setText(R.string.des_bubble_sort);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void performSort(final int step) {
		mCurrentI = step - 1;
		performCompare();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void performCompare(){
		final TextView tvJ = mIntToTv.get(mUnsorted[mJ]);
		final TextView tvJJ = mIntToTv.get(mUnsorted[mJ + 1]);
		tvJ.animate().translationY(mTranslationY).setDuration(500).start();
		tvJJ.animate().translationY(mTranslationY).setDuration(500).withEndAction(new Runnable() {
			@Override
			public void run() {
				if(mUnsorted[mJ] > mUnsorted[mJ + 1]){
					final TextView tvJ = mIntToTv.get(mUnsorted[mJ]);
					tvJ.animate().translationX(mTranslationX + tvJ.getTranslationX()).setDuration(500).start();
					final TextView tvJj = mIntToTv.get(mUnsorted[mJ + 1]);
					tvJj.animate().translationX(- mTranslationX + tvJj.getTranslationX()).setDuration(500).withEndAction(new Runnable() {
						@Override
						public void run() {
							int tmp = mUnsorted[mJ];
							mUnsorted[mJ] = mUnsorted[mJ + 1];
							mUnsorted[mJ + 1] = tmp;
							reset(tvJ, tvJj);
						}
					}).start();
				}else{
					reset(tvJ, tvJJ);
				}
			}
		}).start();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void reset(TextView tvJ, TextView tvJJ) {
		tvJ.animate().translationY(0).setDuration(500).start();
		tvJJ.animate().translationY(0).setDuration(500).withEndAction(new Runnable() {
			@Override
			public void run() {
				mJ++;
				if(mJ < mUnsorted.length - mCurrentI - 1) {
					performCompare();
				}else{
					showResult();
					mJ = 0;
				}
			}
		}).start();
	}
}
