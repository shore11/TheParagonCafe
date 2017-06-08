package com.aaron.theparagoncafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void sendMessage(View view) {



        String tag = "sendMessage";


        //Intent intent = new Intent(this, MenuActivity.class);

        //startActivity(intent);
        // Do something in response to button
    }

}
