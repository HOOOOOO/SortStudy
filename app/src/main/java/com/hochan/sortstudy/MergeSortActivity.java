package com.hochan.sortstudy;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MergeSortActivity extends BaseSortActiviy {

	static void merge_sort(int unsorted[], int low, int high) {
		int mid = (low + high) / 2;
		if(low < high){
			merge_sort(unsorted, low, mid);
			merge_sort(unsorted, mid + 1, high);
			merge(unsorted, low, mid, high);
		}
	}

	static void merge(int unsortred[], int low, int mid, int high){
		int[] tmp = new int[high - low + 1];
		int i = low;
		int j = mid + 1;
		int k = 0;
		while (i <= mid && j <= high){
			if(unsortred[i] < unsortred[j]){
				tmp[k++] = unsortred[i++];
			}else{
				tmp[k++] = unsortred[j++];
			}
		}

		// 把左边剩余的数移入数组
		while (i <= mid) {
			tmp[k++] = unsortred[i++];
		}

		// 把右边边剩余的数移入数组
		while (j <= high) {
			tmp[k++] = unsortred[j++];
		}

		System.out.println();
		// 把新数组中的数覆盖nums数组
		for (int k2 = 0; k2 < tmp.length; k2++) {
			unsortred[k2 + low] = tmp[k2];
		}
	}

	private static final int SORT = 0;
	private static final int MERGE = 1;
	private List<PerformStep> mPerformSteps = new ArrayList<>();

	@Override
	public void setDescription(TextView textView) {
		textView.setText(R.string.des_merge_sort);
	}

	@Override
	public int calculateSteps() {
		int[] clone = mUnsorted.clone();
		mPerformSteps.clear();
		performMergeSort(clone, 0, clone.length - 1);
		System.out.println(mPerformSteps.size());
		return mPerformSteps.size();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void performSort(int step) {
		PerformStep performStep = mPerformSteps.get(step);
		if (performStep.mMethod == SORT) {
			for (int i = performStep.mLow; i <= performStep.mHigh; i++) {
				TextView textView = mIntToTv.get(mUnsorted[i]);
				if (i != performStep.mHigh) {
					textView.animate()
							.translationY(textView.getTranslationY() + mTranslationY)
							.setDuration(500)
							.start();
				} else {
					textView.animate().translationY(textView.getTranslationY() + mTranslationY).setDuration(500).withEndAction(new Runnable() {
						@Override
						public void run() {
							stepFinish();
						}
					}).start();
				}
			}
		} else {
			mTmp = new int[performStep.mHigh - performStep.mLow + 1];
			performCompare(performStep, performStep.mLow, performStep.mMid + 1, 0);
		}
	}

	int[] mTmp = new int[]{};

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void performCompare(final PerformStep performStep, final int i, final int j, final int k) {
		if (i <= performStep.mMid && j <= performStep.mHigh) {
			final TextView tvI = mIntToTv.get(mUnsorted[i]);
			final TextView tvJ = mIntToTv.get(mUnsorted[j]);
			final int valueI = mUnsorted[i];
			final int valueJ = mUnsorted[j];
			tvI.animate().translationY(tvI.getTranslationY() + mTranslationY).setDuration(500).start();
			tvJ.animate().translationY(tvJ.getTranslationY() + mTranslationY).setDuration(500).withEndAction(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (valueI < valueJ) {
						mTmp[k] = valueI;
						tvJ.animate()
								.translationY(tvJ.getTranslationY() - mTranslationY)
								.setDuration(500)
								.start();
						System.out.println(" "+i+" "+performStep.mLow+" "+k);
						tvI.animate()
								.translationY(tvI.getTranslationY() + mTranslationY)
								.translationX(tvI.getTranslationX() - (i - performStep.mLow - k) * mTranslationX)
								.setDuration(500)
								.withEndAction(new Runnable() {
									@Override
									public void run() {
										try {
											Thread.sleep(1000);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										performCompare(performStep, i + 1, j, k + 1);
									}
								})
								.setDuration(500)
								.start();
					} else {
						mTmp[k] = valueJ;
						tvI.animate()
								.translationY(tvI.getTranslationY() - mTranslationY)
								.setDuration(500)
								.start();
						tvJ.animate()
								.translationY(tvJ.getTranslationY() + mTranslationY)
								.translationX(tvJ.getTranslationX() - (j - performStep.mLow - k) * mTranslationX)
								.setDuration(500)
								.withEndAction(new Runnable() {
									@Override
									public void run() {
										performCompare(performStep, i, j + 1, k + 1);
									}
								})
								.setDuration(500)
								.start();
					}
				}
			}).start();
		} else {
			int tmpI = i;
			int tmpK = k;
			while (tmpI <= performStep.mMid) {
				mTmp[tmpK] = mUnsorted[tmpI];
				TextView tv = mIntToTv.get(mUnsorted[tmpI]);
				if (tmpI == performStep.mMid) {
					final int endK = tmpK;
					tv.animate()
							.translationY(tv.getTranslationY() + mTranslationY * 2)
							.translationX(tv.getTranslationX() - (tmpI - performStep.mLow - k) * mTranslationX)
							.withEndAction(new Runnable() {
								@Override
								public void run() {
									for (int i = 0; i < endK; i++) {
										mUnsorted[performStep.mLow + i] = mTmp[i];
									}
									performReset(performStep);
								}
							})
							.setDuration(500)
							.start();
				} else {
					tv.animate()
							.translationY(tv.getTranslationY() + mTranslationY * 2)
							.translationX(tv.getTranslationX() - (tmpI - performStep.mLow - k) * mTranslationX)
							.setDuration(500)
							.start();
				}
				tmpI++;
				tmpK++;
			}
			
			int tmpJ = j;
			while (tmpJ <= performStep.mHigh) {
				mTmp[tmpK] = mUnsorted[tmpJ];
				TextView tv = mIntToTv.get(mUnsorted[tmpJ]);
				if (tmpJ == performStep.mHigh) {
					final int endK = tmpK;
					tv.animate()
							.translationY(tv.getTranslationY() + mTranslationY * 2)
							.translationX(tv.getTranslationX() - (tmpJ - performStep.mHigh - k) * mTranslationX)
							.withEndAction(new Runnable() {
								@Override
								public void run() {
									for (int i = 0; i < endK; i++) {
										mUnsorted[performStep.mLow + i] = mTmp[i];
									}
									performReset(performStep);
								}
							})
							.setDuration(500)
							.start();
				} else {
					tv.animate()
							.translationY(tv.getTranslationY() + mTranslationY * 2)
							.translationX(tv.getTranslationX() - (tmpJ - performStep.mHigh - k) * mTranslationX)
							.setDuration(500)
							.start();
				}
				tmpJ++;
				tmpK++;
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void performReset(PerformStep performStep) {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = performStep.mLow; i <= performStep.mHigh; i++) {
			TextView tv = mIntToTv.get(mUnsorted[i]);
			tv.animate().translationY(tv.getTranslationY() + mTranslationY * -2).setDuration(500).withEndAction(new Runnable() {
				@Override
				public void run() {
					showResult();
				}
			}).start();
		}
	}

	private void performMergeSort(int unsorted[], int low, int high) {
		PerformStep performStep = new PerformStep(SORT, low, 0, high);
		mPerformSteps.add(performStep);
		int mid = (low + high) / 2;
		if (low < high) {
			performMergeSort(unsorted, low, mid);
			performMergeSort(unsorted, mid + 1, high);
			performMerge(unsorted, low, mid, high);
		}
	}

	private void performMerge(int unsortred[], int low, int mid, int high) {
		PerformStep performStep = new PerformStep(MERGE, low, mid, high);
		mPerformSteps.add(performStep);
		int[] tmp = new int[high - low + 1];
		int i = low;
		int j = mid + 1;
		int k = 0;
		while (i <= mid && j <= high) {
			if (unsortred[i] < unsortred[j]) {
				tmp[k++] = unsortred[i++];
			} else {
				tmp[k++] = unsortred[j++];
			}
		}

		// 把左边剩余的数移入数组
		while (i <= mid) {
			tmp[k++] = unsortred[i++];
		}

		// 把右边边剩余的数移入数组
		while (j <= high) {
			tmp[k++] = unsortred[j++];
		}

		// 把新数组中的数覆盖nums数组
		for (int k2 = 0; k2 < tmp.length; k2++) {
			unsortred[k2 + low] = tmp[k2];
		}
	}

	class PerformStep {
		int mMethod;
		int mLow;
		int mMid;
		int mHigh;

		PerformStep(int method, int low, int mid, int high) {
			this.mMethod = method;
			this.mLow = low;
			this.mMid = mid;
			this.mHigh = high;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		MainActivity.SORT_METHOD.MERGE_SORT.showCode(this);
		return true;
	}
}
