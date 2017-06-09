package com.aaron.theparagoncafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Food food1 = new Food("Ham");
        Food food2 = new Food("Soup");
        Food food3 = new Food("Cheese");

        Food[] values = new Food[] {food1, food2, food3};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i].getName());
        }


        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);

        ListView listview = (ListView) findViewById(R.id.foodList);

        listview.setAdapter(adapter);


    }

    public void createFoodActivity(View view) {
String name = "HELLO";
        Intent intent = new Intent(this, Food.class);

        startActivity(intent);
        // Do something in response to button
    }
}
