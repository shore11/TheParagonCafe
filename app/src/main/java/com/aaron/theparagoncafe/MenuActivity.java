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

        list.add("SPECIALS");
        // ADD MONDAY SPECIALS
        DatabaseReference monday = database.getReference("specials/Monday");
        getSpecials(monday, list, adapter, "MONDAY", 0);

        // ADD TUESDAY SPECIALS
        DatabaseReference tuesday = database.getReference("specials/Tuesday");
        getSpecials(tuesday, list, adapter, "TUESDAY", 100);

        // ADD WEDNESDAY SPECIALS
        DatabaseReference wednesday = database.getReference("specials/Wednesday");
        getSpecials(wednesday, list, adapter, "WEDNESDAY", 1000);

        // ADD THURSDAY SPECIALS
        DatabaseReference thursday = database.getReference("specials/Thursday");
        getSpecials(thursday, list, adapter, "THURSDAY", 100);

        //ADD FRIDAY SPECIALS
        DatabaseReference friday = database.getReference("specials/Friday");
        getSpecials(friday, list, adapter, "FRIDAY", 100);

        // ADD SATURDAY SPECIALS
        DatabaseReference saturday = database.getReference("specials/Saturday");
        getSpecials(saturday, list, adapter, "SATURDAY", 100);

        // ADD BREAKFAST ITEMS TO LIST
        DatabaseReference breakFast = database.getReference("regularFood/breakFast");
        getDatabaseInfo(breakFast, list, adapter, "BREAKFAST", 0);

        // ADD SIDES TO LIST
        DatabaseReference bSides = database.getReference("regularFood/breakFast/sides");
        getDatabaseInfo(bSides, list, adapter, "BREAKFAST SIDES", 1000);

        // ADD DINNER TO LIST
        DatabaseReference dinner = database.getReference("regularFood/dinner");
        getDatabaseInfo(dinner, list, adapter, "DINNER", 100);

        // ADD SANDWICHES TO LIST
        DatabaseReference sandwiches = database.getReference("regularFood/dinner/Sandwiches");
        getDatabaseInfo(sandwiches, list, adapter, "SANDWICHES", 100);

        // ADD DINNER SIDES
        DatabaseReference dSides = database.getReference("regularFood/dinner/sides");
        getDatabaseInfo(dSides, list, adapter, "DINNER SIDES", 100);

        listview.setAdapter(adapter);

        //This opens the food's information when you click on it
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(MenuActivity.this, FoodActivity.class));
            }
        });
    }

    // function to get the specials
    void getSpecials (DatabaseReference dr, final List<String> list, final ArrayAdapter adapter, final String header, int wait){
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.add(header);
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String type = ds.getKey();
                    list.add(type);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    // function to handle retrieving database from any node
    void getDatabaseInfo(DatabaseReference dr, final List<String> list, final ArrayAdapter adapter, final String first, int wait) {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

