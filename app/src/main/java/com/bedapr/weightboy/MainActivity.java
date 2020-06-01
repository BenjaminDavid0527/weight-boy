package com.bedapr.weightboy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bedapr.weightboy.weights.Weights;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick (View view) {
        TextView resultText = findViewById(R.id.resultTextView);
        resultText.setAlpha(0.0f);

        EditText weightText = findViewById(R.id.weightEditText);
        String input = weightText.getText().toString();
        if (input.isEmpty()) {
            Log.i("This stuff is", "onClick: ");
            toast("Please enter a weight value!");
            return;
        }

        //vvTHIS WILL NEED TO BE REPLACED TO ALLOW CUSTOM WEIGHT SETS, INC. METRIC OPTIONSvv
        double[] sizes = {45, 45, 25, 15, 10, 5, 2.5}; // TODO make weight sets importable + interchangeable
        //^^THIS WILL NEED TO BE REPLACED TO ALLOW CUSTOM WEIGHT SETS, INC. METRIC OPTIONS^^

        double weight = Double.parseDouble(input);
        if (weight < sizes[0]) {
            toast("Sorry, weight is too low for this weight set!");
            return;
        }

        Weights weights = new Weights(weight, sizes);

        resultText.setText(weights.toString());
        resultText.setAlpha(1.0f);
        closeKeyboard(view);
    }

    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void toast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }

}
