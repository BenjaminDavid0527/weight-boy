/*
    Author:         Benjamin Price

    Description:    Fragment for displaying gym page. OnClick sends TargetWeight query
                    to GymViewModel for calculation, after some rudimentary input
                    verification.

 */
package com.bedapr.weightboy.ui.gym;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bedapr.weightboy.R;

/*
   Note to self: Fragments are responsible for displaying data,
                 ViewModels are responsible for storing UI state/data
 */
public class GymFragment extends Fragment implements View.OnClickListener {

    private GymViewModel gymViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gymViewModel =  // Note that gymViewModel is attached to the activity's
                // lifecyle, not the fragment's
                ViewModelProviders.of(getActivity()).get(GymViewModel.class);

        View root = inflater.inflate(R.layout.fragment_gym, container, false);

        final TextView textView = root.findViewById(R.id.text_gym);
        gymViewModel.getWeightText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final EditText editText = root.findViewById(R.id.editTextGymWeight);
        gymViewModel.getEditText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                editText.setText(s);
            }
        });

        final Button gymWeightButton = root.findViewById(R.id.gymWeightButton);
        gymWeightButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {

        final EditText weightText = getActivity().findViewById(R.id.editTextGymWeight);
        weightText.clearFocus();
        closeKeyboard(view);

        String input = weightText.getText().toString();
        if (input.isEmpty()) {
            toast(R.string.toast_no_weight);
            return;
        }

        //vvTHIS WILL NEED TO BE REPLACED TO ALLOW CUSTOM WEIGHT SETS, INC. METRIC OPTIONSvv
        double[] sizes = {45, 45, 25, 15, 10, 5, 2.5};
        //^^THIS WILL NEED TO BE REPLACED TO ALLOW CUSTOM WEIGHT SETS, INC. METRIC OPTIONS^^

        double weight = Double.parseDouble(input);
        if (weight < sizes[0]) toast(R.string.toast_less_weight);
        else if (weight == sizes[0]) toast(R.string.toast_bar_weight);
        else  /*(weight > bar)*/        gymViewModel.calcWeight(weight, sizes, input);
    }


    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void toast(int resId) {
        Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_LONG).show();
    }
}