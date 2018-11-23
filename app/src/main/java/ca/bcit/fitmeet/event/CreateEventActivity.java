package ca.bcit.fitmeet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import ca.bcit.fitmeet.model.Event;

public class CreateEventActivity extends AppCompatActivity {
    private static final String TAG = "Sample";
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private static final String STATE_TEXTVIEW = "STATE_TEXTVIEW";

    private FirebaseAuth mAuth;
    private String userToken;
    private FloatingActionButton saveEvent;
    private EditText dateTime;
    private Date eventDate;
    private SwitchDateTimeDialogFragment dateTimeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null ) {
            startActivity(new Intent(CreateEventActivity.this, LoginActivityMain.class));
            finish();
        } else {
            userToken = currentUser.getUid();
        }

        setContentView(R.layout.activity_create_event);

        Toolbar toolbar_main = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dateTimeFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        dateTime = findViewById(R.id.event_date);
        dateTime.setInputType(InputType.TYPE_NULL);
        saveEvent = findViewById(R.id.save_event);
        setListener();
        initialiseDateTime();

    }

    private void setListener() {
        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Re-init each time
                dateTimeFragment.startAtCalendarView();
                dateTimeFragment.setDefaultDateTime(new GregorianCalendar().getTime());
                dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);

                InputMethodManager imm = (InputMethodManager) CreateEventActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                //        hideKeyboard(CreateEventActivity.this);
            }
        });

        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEnteredData();
            }
        });
    }

    private void getEnteredData() {
        EditText eventName = findViewById(R.id.event_name);
        EditText description = findViewById(R.id.event_description);
        EditText location = findViewById(R.id.event_location);
        EditText tags = findViewById(R.id.event_tags);
        dateTime = findViewById(R.id.event_date);

        String eventNameString = eventName.getText().toString();
        String descriptionString = description.getText().toString();
        String locationString = location.getText().toString();
        String dateTimeString = dateTime.getText().toString();
        String tagsString = "";
        addToDB(eventNameString, descriptionString, locationString, dateTimeString, tagsString);
    }

    private void addToDB(String eventNameString, String descriptionString, String locationString, String dateTimeString, String tagsString) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventReference = database.getReference("events");

        String eventID = eventReference.push().getKey();

        Event newEvent = new Event(eventID, userToken, eventNameString, descriptionString, locationString, eventDate, new ArrayList<String>(Arrays.asList("Running", "Yoga")));
        if (eventID != null) {
            Task setValueTask = eventReference.child(eventID).setValue(newEvent);
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initialiseDateTime() {
        if(dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    "date",
                    "ok",
                    "cancel",
                    "Clear"
            );
        }
        dateTimeFragment.setTimeZone(TimeZone.getDefault());
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm", java.util.Locale.getDefault());
        // Assign unmodifiable values
        dateTimeFragment.set24HoursMode(false);
        dateTimeFragment.setHighlightAMPMSelection(false);
        dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2018, Calendar.JANUARY, 1).getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2028, Calendar.DECEMBER, 31).getTime());

        // Define new day and month format
        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e(TAG, e.getMessage());
        }

        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                eventDate = date;
                dateTime.setText(myDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
                dateTime.setText(null);
            }
        });
    }

    public static void hideKeyboard(Activity activity) {

        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }

    }









































//    private void addSampleData() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("events");
//
////        for (Event e: Event.sampleData) {
////            DatabaseReference pushedPostRef = myRef.push();
////            String dataEntryID = pushedPostRef.getKey();
////            e.setEventId(dataEntryID);
////            e.setHostToken(userToken);
////            myRef.child(dataEntryID).setValue(e);
////        }
//    }
//
////    private void addSampleDate() {
////        FirebaseDatabase database = FirebaseDatabase.getInstance();
////        DatabaseReference myRef = database.getReference("events");
////
////        for (Event e: Event.sampleData) {
////            DatabaseReference pushedPostRef = myRef.push();
////            String dataEntryID = pushedPostRef.getKey();
////            Map<String, Object> testDataMap = e.toMap();
////            myRef.child(dataEntryID).setValue(e);
////        }
////    }
//
//    public void testAddData(View v){
//        EditText name =  findViewById(R.id.event_name);
//        EditText description =  findViewById(R.id.event_description);
//        EditText location =  findViewById(R.id.event_location);
//        EditText time =  findViewById(R.id.event_date);
//
//        String descriptionString = description.getText().toString();
//        String nameString = name.getText().toString();
//        String locationString = location.getText().toString();
//        String timeString = time.getText().toString();
//        addTestData(descriptionString, nameString, locationString, timeString);
//
//    }
//
//    public void addTestData(String description, String name, String location, String time){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("events");
//
//        //This line is to allow duplicate data, by creating data under a new leaf node every time
//        DatabaseReference pushedPostRef = myRef.push();
//        String dataEntryID = pushedPostRef.getKey();
//
//        //Create event
//        //Event event = new Event(dataEntryID, name, userToken , new Date(), location, description, null);
//
//        //Firebase takes a map to insert dat into its leaf nodes
//      //  Map<String, Object> testDataMap = event.toMap();
//
//        //Puts map data into child of testData
//        //myRef.child(dataEntryID).setValue(testDataMap);
//
//    }
//
//
//


}
