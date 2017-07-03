package com.aaron.theparagoncafe;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * This is the FoodActivity that pops up when you click on a food
 * item from the menu
 *
 * @author Cameron Lyman
 */
public class FoodActivity extends Activity {

    private static final String TAG = "FoodActivity";
    private static final double SIZE_MULTIPLIER = 0.7;

    TextView mFoodName;
    TextView mFoodPrice;
    TextView mFoodTime;
    PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout( (int)( width*SIZE_MULTIPLIER),
                ((int) (height*SIZE_MULTIPLIER)) );

        mFoodName = (TextView) findViewById(R.id.foodName);
        mFoodPrice = (TextView) findViewById(R.id.foodPrice);
        mFoodTime = (TextView) findViewById(R.id.foodTime);

        //Setting the food information
        Log.d(TAG, "Attempting to set FoodName");
        mFoodName.setText(getIntent().getStringExtra("EXTRA_SELECTED_ITEM"));
    }


}
