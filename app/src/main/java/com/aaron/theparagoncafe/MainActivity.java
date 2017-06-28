package com.aaron.theparagoncafe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Main Activity
 * starting point for the app
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "Main Activity started");
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onStop(){
        super.onStop();
        Context context = getApplicationContext();

        Toast toast = Toast.makeText(context,"Enjoy", Toast.LENGTH_LONG);
        toast.show();
    }

    public void startMenu(View view) {
        Log.d("MainActivity", "Starting next activity");

        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);
        // Do something in response to button
    }

}
