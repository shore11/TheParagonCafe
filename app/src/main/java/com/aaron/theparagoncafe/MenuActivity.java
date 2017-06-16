package com.aaron.theparagoncafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;


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


        String[] values = new String[] { "Soup", "Meat", "Crackers"};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                values);

        ListView listview = (ListView) findViewById(R.id.foodList);

        listview.setAdapter(adapter);

        // when things change in the data base these functions handle that
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                //Comment newComment = dataSnapshot.getValue(Comment.class);
                //String commentKey = dataSnapshot.getKey();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addChildEventListener(mChildEventListener);
    }
}
