package ca.bcit.fitmeet;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class CreateEventActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String userToken = "";
    private Button saveEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Toolbar toolbar_main = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        saveEventButton = findViewById(R.id.button);
        saveEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testAddData(view);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userToken = currentUser.getUid();
    }

    public void testAddData(View v){
        EditText name =  findViewById(R.id.event_name);
        EditText description =  findViewById(R.id.event_description);
        EditText location =  findViewById(R.id.event_location);
        EditText time =  findViewById(R.id.event_date);

        String descriptionString = description.getText().toString();
        String nameString = name.getText().toString();
        String locationString = location.getText().toString();
        String timeString = time.getText().toString();
        addTestData(descriptionString, nameString, locationString, timeString);
    }

    public void addTestData(String description, String name, String location, String time){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("events");

        //This line is to allow duplicate data, by creating data under a new leaf node every time
        DatabaseReference pushedPostRef = myRef.push();
        String dataEntryID = pushedPostRef.getKey();

        //Create event
        Event event = new Event(dataEntryID, name, userToken, time, location, description);

        //Firebase takes a map to insert dat into its leaf nodes
        Map<String, Object> testDataMap = event.toMap();

        //Puts map data into child of testData
        myRef.child(dataEntryID).setValue(testDataMap);

    }




}
