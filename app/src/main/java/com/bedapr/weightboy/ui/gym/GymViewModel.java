/*
    Author:         Benjamin Price

    Description:    ViewModel intended to store data for the GymFragment class.
                    However, its lifecycle is intended to be tied to MainActivity, so that
                    the user has seemless swapping between fragments.
                    Currently stores GymFragment's EditText and TextView data.
 */
package com.bedapr.weightboy.ui.gym;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bedapr.weightboy.weights.Weights;

public class GymViewModel extends ViewModel {

    private MutableLiveData<String> mWeightText;
    private MutableLiveData<String> mEditText;

    public GymViewModel() {
        mWeightText = new MutableLiveData<>();
        mEditText = new MutableLiveData<>();
    }

    public LiveData<String> getWeightText() {
        return mWeightText;
    }

    public LiveData<String> getEditText() {
        return mEditText;
    }

    public void calcWeight(double weightGoal, double[] sizes, String input) {
        try {
            mWeightText.setValue(new Weights(weightGoal, sizes).toString());
            mEditText.setValue(input);
        } catch (Exception e) {
            Log.d("GymViewModel", "Failed to update weights");
        }
    }
}