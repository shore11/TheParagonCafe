package com.aaron.theparagoncafe;

import android.content.ClipData;
import android.content.Intent;
import android.preference.PreferenceActivity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.aaron.theparagoncafe.R.id.parent;


public class MenuActivity extends AppCompatActivity {

    //TAG CONSTANT
    private static final String TAG = "MenuActivity";

    // variable to access items in the database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    // when items in the database change this can update the fields
    private ChildEventListener mChildEventListener;

    public static boolean ready = false;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MenuActivity", "Menu Activity started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String[] values = new String[] { "Soup", "Meat", "Crackers", "Ramen"};

        final List<String> list = new ArrayList<>();
        //list to hold Food as objects
        final List<Food> fList = new ArrayList<>();

        final ListView listview = (ListView) findViewById(R.id.foodList);
        List<String> bufferList = new ArrayList<>();
        // had to be made before putting items into list
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);


        //list.add("SPECIALS");

        DatabaseReference root = database.getReference();

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // root node returns regular and specials
                Map<String, Map<String, Map<String, List<String>>>> root = new HashMap<String, Map<String, Map<String, List<String>>>>();
                //Map<String, DataSnapshot> specials = (Map<String, DataSnapshot>) root.get("specials").getValue();
//                DataSnapshot monday = specials.get("Monday");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   // list.add(ds.getKey());
                    root.put(ds.getKey(), new HashMap<String, Map<String, List<String>>>());
                    for (DataSnapshot ds1 : ds.getChildren()){
                      //  list.add(ds1.getKey());
                        root.get(ds.getKey()).put(ds1.getKey(), new HashMap<String, List<String>>());
                        for (DataSnapshot ds2 : ds1.getChildren()){
                         //   list.add(ds2.getKey());
                            if (ds2.getKey().equals("sides")){
                                root.get(ds.getKey()).get(ds1.getKey()).put(ds2.getKey(),new ArrayList<String>());
                                for (DataSnapshot ds3 : ds2.getChildren()){
                             //       list.add(ds3.getKey());
                                    root.get(ds.getKey()).get(ds1.getKey()).get(ds2.getKey()).add(ds3.getKey());
                                }
                            }
                            else{
                                root.get(ds.getKey()).get(ds1.getKey()).put(ds2.getKey(), null);
                            }
                        }
                    }
                }
                list.add("Specials");
                list.add("Monday");
                for (String s : root.get("specials").get("Monday").keySet()){
                    list.add(s);
                }
                list.add("Tuesday");
                for (String s : root.get("specials").get("Tuesday").keySet()){
                    list.add(s);
                }
                list.add("Wednesday");
                for (String s : root.get("specials").get("Wednesday").keySet()){
                    list.add(s);
                }
                list.add("Thursday");
                for (String s : root.get("specials").get("Thursday").keySet()){
                    list.add(s);
                }
                list.add("Friday");
                for (String s : root.get("specials").get("Friday").keySet()){
                    list.add(s);
                }
                list.add("Saturday");
                for (String s : root.get("specials").get("Saturday").keySet()){
                    list.add(s);
                }
                list.add("Menu");
                list.add("BreakFast");
                for (String s : root.get("regularFood").get("breakFast").keySet()){
                    if (!s.equals("sides")) {
                        list.add(s);
                    }
                }
                // sides
                list.add("BreakFast Sides");
                for (String s : root.get("regularFood").get("breakFast").get("sides")){
                    list.add(s);
                }
                list.add("Dinner");
                for (String s : root.get("regularFood").get("dinner").keySet()){
                    if (!s.equals("sides")) {
                        list.add(s);
                    }
                }
                // sides
                list.add("Dinner Sides");
                for (String s : root.get("regularFood").get("dinner").get("sides")){
                    list.add(s);
                }
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        Log.d("MenuActivity", "Ended pull from firebase");
//        finish();
//        startActivity(getIntent());
//        adapter.notifyDataSetChanged();
//        listview.refreshDrawableState();
        listview.setAdapter(adapter);


        //This opens the food's information when you click on it
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // if statements for non clickable
                String selectedFromList = (String) (listview.getItemAtPosition(position));
                Log.d(TAG, "making new intent for FoodActivity");
                Intent intent = new Intent(MenuActivity.this, FoodActivity.class);
                Log.d(TAG, "intent for FoodActivity created, attempting to apply putExtra");
                intent.putExtra("EXTRA_SELECTED_ITEM", selectedFromList);
                Log.d(TAG, "putExtra: " +selectedFromList + " applied, attempting to start FoodActivity");
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();


    }
}

