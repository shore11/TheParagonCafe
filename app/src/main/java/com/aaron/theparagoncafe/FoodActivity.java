package com.aaron.theparagoncafe;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class FoodActivity extends AppCompatActivity {

    private static final String TAG = "FoodActivity";

    TextView mFoodName;
    TextView mFoodPrice;
    TextView mFoodTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        mFoodName = (TextView) findViewById(R.id.foodName);
        mFoodPrice = (TextView) findViewById(R.id.foodPrice);
        mFoodTime = (TextView) findViewById(R.id.foodTime);

        //THIS NEEDS TO BE CHANGED LATER TO PULL FROM DATABASE
        Log.d(TAG, "Attempting to set FoodName");
        mFoodName.setText(getIntent().getStringExtra("EXTRA_SELECTED_ITEM"));
    }


}
