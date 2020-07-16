/*
Main Activity class.

Author: Benjamin Price
Version : 0.1
 */
package com.bedapr.weightboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bedapr.weightboy.weights.Weights;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

/*
    Takes the weight given by user and outputs the most efficient
    way to rack to the screen.
 */
    public void onClick (View view) {
        TextView resultText = findViewById(R.id.resultTextView);
        resultText.setVisibility(View.INVISIBLE);

        EditText weightText = findViewById(R.id.weightEditText);

        weightText.clearFocus();
        closeKeyboard(view);

        String input = weightText.getText().toString();
        if (input.isEmpty()) {
            toast("Please enter a weight value!");
            return;
        }

        //vvTHIS WILL NEED TO BE REPLACED TO ALLOW CUSTOM WEIGHT SETS, INC. METRIC OPTIONSvv
        double[] sizes = {45, 45, 25, 15, 10, 5, 2.5};
        //^^THIS WILL NEED TO BE REPLACED TO ALLOW CUSTOM WEIGHT SETS, INC. METRIC OPTIONS^^

        double weight = Double.parseDouble(input);
        if (weight < sizes[0]) {
            toast("Sorry, weight is lower than the bar. You'll get there!");
            return;
        }

        Weights weights = new Weights(weight, sizes);

        resultText.setText(weights.toString());
        resultText.setVisibility(View.VISIBLE);
    }

    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void toast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }

}
