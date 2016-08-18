package com.hochan.sortstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SelectionSortActivity extends BaseSortActiviy {

	static void selection_sort(int[] unsorted){
		for(int i = 0; i < unsorted.length; i++){
			int min = i;
			for(int j = i; i < unsorted.length; j++){
				if(unsorted[min] > unsorted[j]){
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
	public void performSort(int step) {

	}
}
