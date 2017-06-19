package com.aaron.theparagoncafe;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.aaron.theparagoncafe.R.id.parent;


public class MenuActivity extends AppCompatActivity {

    // variable to access items in the database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    // when items in the database change this can update the fields
    private ChildEventListener mChildEventListener;
    private String course;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String[] values = new String[] { "Soup", "Meat", "Crackers", "Ramen"};

        final List<String> list = new ArrayList<String>();
        //list to hold Food as objects
        final List<Food> foodList = new ArrayList<>();


        ListView listview = (ListView) findViewById(R.id.foodList);

        // had to be made before putting items into list
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);

        // ADD BREAKFAST ITEMS TO LIST
        DatabaseReference breakFast = database.getReference("regularFood/breakFast");
        course = "BREAKFAST";
        getDatabaseInfo(breakFast, list, adapter, course);

        // ADD SIDES TO LIST
        DatabaseReference bSides = database.getReference("regularFood/breakFast/sides");
        course = "BREAKFAST SIDES";
        getDatabaseInfo(bSides, list, adapter, course);

        // ADD DINNER TO LIST
        DatabaseReference dinner = database.getReference("regularFood/dinner");
        course = "DINNER";
        getDatabaseInfo(dinner, list, adapter, course);

        // ADD SANDWICHES TO LIST
        DatabaseReference sandwiches = database.getReference("regularFood/dinner/Sandwiches");
        course = "SANDWICHES";
        getDatabaseInfo(sandwiches, list, adapter, course);

        // ADD DINNER SIDES
        DatabaseReference dSides = database.getReference("regularFood/dinner/sides");
        course = "DINNER SIDES";
        getDatabaseInfo(dSides, list, adapter, course);

        listview.setAdapter(adapter);

        //This opens the food's information when you click on it
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(MenuActivity.this, FoodActivity.class));
            }
        });
    }

    // function to handle retrieving database from any node
    public void getDatabaseInfo(DatabaseReference dr, final List<String> list, final ArrayAdapter adapter, final String first) {
        Log.d("This method was called", "This method was called");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.add(first);
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    //String food = ds.getKey();
                    Food food = ds.getValue(Food.class);
                    if (food.getName() != null) {
                        list.add(food.getName());
                    }
//                    if (food != null){
//                        foodList.add(food);
//                    }
                    // tell the adapter every time something is added so it can update
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}

