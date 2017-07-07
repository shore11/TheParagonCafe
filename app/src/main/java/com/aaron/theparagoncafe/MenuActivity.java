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
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.aaron.theparagoncafe.R.id.parent;

/**
 * Menu Activity
 * Handles everything to do with the Menu
 */
public class MenuActivity extends AppCompatActivity {

    //TAG CONSTANT
    private static final String TAG = "MenuActivity";

    // variable to access items in the database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ValueEventListener valueEventListener;
    private DatabaseReference root;

    //Create our expandableList
    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;

    private Map<String, Food> foodMap = new HashMap<String, Food>();
    private Food current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MenuActivity", "Menu Activity started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final List<String> list = new ArrayList<>();
        //list to hold Food as objects
        final List<Food> fList = new ArrayList<>();


        //get reference of the ExpandableListView
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        // create the adapter by passing your ArrayList data
        listAdapter = new CustomAdapter(MenuActivity.this, deptList);
        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(listAdapter);

        //expand all the Groups
        //expandAll();

        // Intent for ChildClickListener
        final Intent intent = new Intent(this, FoodActivity.class);

        // setOnChildClickListener listener for child row click
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //get the child info
                ChildInfo detailInfo =  headerInfo.getProductList().get(childPosition);
                // Get the right food item and store it in current
                switch(headerInfo.getName()) {
                    case "Monday Specials":
                        current = foodMap.get("Monday " + detailInfo.getName());
                        break;
                    case "Tuesday Specials":
                        current = foodMap.get("Tuesday " + detailInfo.getName());
                        break;
                    case "Wednesday Specials":
                        current = foodMap.get("Wednesday " + detailInfo.getName());
                        break;
                    case "Thursday Specials":
                        current = foodMap.get("Thursday " + detailInfo.getName());
                        break;
                    case "Friday Specials":
                        current = foodMap.get("Friday " + detailInfo.getName());
                        break;
                    case "Saturday Specials":
                        current = foodMap.get("Saturday " + detailInfo.getName());
                        break;
                    default:
                        current = foodMap.get(detailInfo.getName());
                        break;
                }
                //display it or do something with it
                Toast.makeText(getBaseContext(), " Clicked on :: " + headerInfo.getName()
                        + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();



                //WE NEED TO CHANGE DUMMY TO THE SELECTED FOOD ITEM
                intent.putExtra("EXTRA_NAME", current.getName());
                intent.putExtra("EXTRA_PRICE", current.getSinglePrice());
                intent.putExtra("EXTRA_COMBO", current.getComboPrice());
                intent.putExtra("EXTRA_TIME", current.getTime());
                intent.putExtra("EXTRA_DESC", current.getDescription());
                startActivity(intent);
                return false;
            }
        });
        // setOnGroupClickListener listener for group heading click
        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //display it or do something with it
                Toast.makeText(getBaseContext(), " Header is :: " + headerInfo.getName(),
                        Toast.LENGTH_LONG).show();

                return false;
            }
        });

        root = database.getReference();
        /**
         * <p>
         *    This will loop through each node and collect its
         *    Children in a map and the pattern continues down the nodes
         *    The Children become keys and their cildren are put in a map
         * </p>
         * @param ValueEventListener to attach to the specific node
         *
         */
        valueEventListener = root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // root node returns regular and specials
                Map<String, Map<String, Map<String, List<String>>>> root = new HashMap<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   // list.add(ds.getKey());
                    root.put(ds.getKey(), new HashMap<String, Map<String, List<String>>>());
                    for (DataSnapshot ds1 : ds.getChildren()){
                        //fList.add(ds1.getValue(Food.class));
                        root.get(ds.getKey()).put(ds1.getKey(), new HashMap<String, List<String>>());
                        for (DataSnapshot ds2 : ds1.getChildren()){
                            fList.add(ds2.getValue(Food.class));
                            if (ds2.getKey().equals("sides")){
                                root.get(ds.getKey()).get(ds1.getKey()).put(ds2.getKey(),new ArrayList<String>());
                                for (DataSnapshot ds3 : ds2.getChildren()){
                                    fList.add(ds3.getValue(Food.class));
                                    root.get(ds.getKey()).get(ds1.getKey()).get(ds2.getKey()).add(ds3.getKey());
                                }
                            }
                            else if (ds2.getKey().equals("Sandwiches")){
                                root.get(ds.getKey()).get(ds1.getKey()).put(ds2.getKey(),new ArrayList<String>());
                                for (DataSnapshot ds3 : ds2.getChildren()){
                                    fList.add(ds3.getValue(Food.class));
                                    root.get(ds.getKey()).get(ds1.getKey()).get(ds2.getKey()).add(ds3.getKey());
                                }
                            }
                            else {
                                root.get(ds.getKey()).get(ds1.getKey()).put(ds2.getKey(), null);
                            }
                        }
                    }
                }
                for (String s : root.get("specials").get("Monday").keySet()){
                    loadData("Monday Specials", s);
                    listAdapter.notifyDataSetChanged();
                }
                for (String s : root.get("specials").get("Tuesday").keySet()){
                    loadData("Tuesday Specials", s);
                    listAdapter.notifyDataSetChanged();
                }
                for (String s : root.get("specials").get("Wednesday").keySet()){
                    loadData("Wednesday Specials", s);
                    listAdapter.notifyDataSetChanged();
                }
                for (String s : root.get("specials").get("Thursday").keySet()){
                    loadData("Thursday Specials", s);
                    listAdapter.notifyDataSetChanged();
                }
                for (String s : root.get("specials").get("Friday").keySet()){
                    loadData("Friday Specials", s);
                    listAdapter.notifyDataSetChanged();
                }
                for (String s : root.get("specials").get("Saturday").keySet()){
                    loadData("Saturday Specials", s);
                    listAdapter.notifyDataSetChanged();
                }
                for (String s : root.get("regularFood").get("breakFast").keySet()){
                    if (!s.equals("sides")) {
                        loadData("Breakfast", s);
                        listAdapter.notifyDataSetChanged();
                    }
                }
                // sides
                for (String s : root.get("regularFood").get("breakFast").get("sides")){
                    loadData("Breakfast Sides", s);
                    listAdapter.notifyDataSetChanged();
                }
                for (String s : root.get("regularFood").get("dinner").keySet()){
                    if (!s.equals("sides") && !s.equals("Sandwiches")) {
                        loadData("Dinner", s);
                        listAdapter.notifyDataSetChanged();
                    }
                }
                for (String s : root.get("regularFood").get("dinner").get("Sandwiches")){
                    loadData("Sandwiches", s);
                    listAdapter.notifyDataSetChanged();
                }

                // sides
                for (String s : root.get("regularFood").get("dinner").get("sides")){
                    loadData("Dinner Sides", s);
                    listAdapter.notifyDataSetChanged();
                }
                for (Food food : fList){
                    foodMap.put(food.getName(), food);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        Log.d("MenuActivity", "Ended pull from firebase");

    }
    // remove the listener when activity is done
    @Override
    protected void onDestroy() {
        super.onDestroy();
        root.removeEventListener(valueEventListener);
    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.expandGroup(i);
        }
    }

    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.collapseGroup(i);
        }
    }

    //load some initial data into out list
    private void loadData(String group, String name){


        addProduct(group, name);


    }



    //here we maintain our products in various departments
    private int addProduct(String department, String product){

        int groupPosition = 0;

        //check the hash map if the group already exists
        GroupInfo headerInfo = subjects.get(department);
        //add the group if doesn't exists
        if(headerInfo == null){
            headerInfo = new GroupInfo();
            headerInfo.setName(department);
            subjects.put(department, headerInfo);
            deptList.add(headerInfo);
        }

        //get the children for the group
        ArrayList<ChildInfo> productList = headerInfo.getProductList();
        //size of the children list
        int listSize = productList.size();
        //add to the counter
        listSize++;

        //create a new child and add that to the group
        ChildInfo detailInfo = new ChildInfo();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);

        //find the group position inside the list
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }

    /**
     * Sends an email with the comment in it
     * @param view
     */
    public void sendComment(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String comment = editText.getText().toString();
        if (!comment.equals("")) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"jaredvbrown@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Paragon Cafe Comment");
            intent.putExtra(Intent.EXTRA_TEXT   , comment);
            try {
                startActivity(Intent.createChooser(intent, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MenuActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
        editText.setText("");
    }

}

