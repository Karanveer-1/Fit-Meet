package ca.bcit.fitmeet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String userToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userToken = currentUser.getUid();
        TextView tokenView = findViewById(R.id.event_host_token);
        tokenView.setText(userToken);

    }


    public void testAddData(View v){
        EditText description =  findViewById(R.id.event_description);
        EditText name =  findViewById(R.id.event_name);
        EditText location =  findViewById(R.id.event_location);
        EditText time =  findViewById(R.id.event_time);

        String descriptionString = description.getText().toString();
        String nameString = name.getText().toString();
        String locationString = location.getText().toString();
        String timeString = location.getText().toString();
        addTestData(descriptionString, nameString, locationString, timeString);


    }


    public void addTestData(String description, String name, String location, String time){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userToken = currentUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("events");

        //This line is to allow duplicate data, by creating data under a new leaf node every time
        DatabaseReference pushedPostRef = myRef.push();
        String dataEntryID = pushedPostRef.getKey();


        //Create event
//        Event event = new Event(dataEntryID, name, userToken, time, location, description);
//        //Firebase takes a map to insert dat into its leaf nodes
//        Map<String, Object> testDataMap = event.toMap();
//
//        //Puts map data into child of testData
//        myRef.child(dataEntryID).setValue(testDataMap);

    }


}
