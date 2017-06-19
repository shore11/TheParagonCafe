package com.aaron.theparagoncafe;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.aaron.theparagoncafe.R.id.parent;


public class MenuActivity extends AppCompatActivity {

    // variable to access items in the database
    private DatabaseReference mDatabase;
    // when items in the database change this can update the fields
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        // this gets the root node of the database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // if you want access to a specific node
        // string cheesbuger = mDatabaseRefrence.getReference().child("keyofchild")
        // to go farther down the tree  child().child().child()


        String[] values = new String[] { "Soup", "Meat", "Crackers", "Ramen"};

        final ArrayList<String> list = new ArrayList<String>();
        //for (int i = 0; i < values.length; ++i) {
        //    list.add(values[i]);
        //}

        ListView listview = (ListView) findViewById(R.id.foodList);

        Food food = new Food("Turkey");
        list.add(food.getName());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                   // Food food = ds.getValue(Food.class);
                    Food food = new Food("Ham");
                    list.add(food.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);
        listview.setAdapter(adapter);


        //This opens the food's information when you click on it
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(MenuActivity.this, FoodActivity.class));
            }
        });
    }


}
