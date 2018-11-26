package ca.bcit.fitmeet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Test2Activity extends AppCompatActivity {
    private ArrayList<String> testDataArray;
    ListView listview;
    DatabaseReference dref;
    ArrayAdapter<String> adapter;
    private FirebaseAuth mAuth;
    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        dref = FirebaseDatabase.getInstance().getReference("events");

        testDataArray = new ArrayList<>();

        testDataArray = retrieve();

        listview=(ListView)findViewById(R.id.list_data);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,testDataArray);
        listview.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userToken = currentUser.getUid();
    }

    //READ
    public ArrayList<String> retrieve() {

        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
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
        });

        return testDataArray;
    }

    private void fetchData(DataSnapshot dataSnapshot) {

        //USE THIS IF YOU WANT TO ISOLATE CURRENT USER CREATED EVENTS
        String hostToken = dataSnapshot.child("hostToken").getValue(String.class);

        if(hostToken.equals(userToken)){
            String name = "";
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                String component = ds.getValue(String.class);
                name = name +   component + " " ;
            }

            testDataArray.add(name);
        }
    }

}
