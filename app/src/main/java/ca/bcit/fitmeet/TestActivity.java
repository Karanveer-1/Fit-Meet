package ca.bcit.fitmeet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

    }


    public void testAddData(View v){


        EditText one =  findViewById(R.id.test_editText_1);
        EditText two =  findViewById(R.id.test_editText_2);
        EditText three =  findViewById(R.id.test_editText_3);

        String oneString = one.getText().toString();
        String twoString = two.getText().toString();
        String threeString = three.getText().toString();

        addTestData(oneString, twoString, threeString);

    }


    public void addTestData(String one, String two, String three){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userToken = currentUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("testData");
        //This line is to allow duplicate data, by creating data under a new leaf node every time
        DatabaseReference pushedPostRef = myRef.push();
        String dataEntryID = pushedPostRef.getKey();
        //Firebase takes a map to insert dat into its leaf nodes
        HashMap<String, Object> testDataMap = new HashMap<>();
        testDataMap.put("1", one);
        testDataMap.put("2", two);
        testDataMap.put("3", three);
        //Puts map data into child of testData
        myRef.child(dataEntryID).setValue(testDataMap);

    }


}
