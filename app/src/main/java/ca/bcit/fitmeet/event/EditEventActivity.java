package ca.bcit.fitmeet.event;

import android.app.Activity;
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
import android.widget.EditText;

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
import java.util.TimeZone;

import ca.bcit.fitmeet.login.LoginActivityMain;
import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.event.model.Event;

public class EditEventActivity extends AppCompatActivity {
    private static final String TAG = "Sample";
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private static final String STATE_TEXTVIEW = "STATE_TEXTVIEW";
    private DatabaseReference eventRef;
    private FirebaseAuth mAuth;
    private String userToken;
    private FloatingActionButton saveEvent;
    private EditText dateTime;
    private Date eventDate;
    private String eventId;
    private String hostId;
    private SwitchDateTimeDialogFragment dateTimeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventId = getIntent().getStringExtra("eventId");
        mAuth = FirebaseAuth.getInstance();

        eventRef = FirebaseDatabase.getInstance().getReference("events").child(eventId);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(ca.bcit.fitmeet.event.EditEventActivity.this, LoginActivityMain.class));
            finish();
        } else {
            userToken = currentUser.getUid();
        }

        setContentView(R.layout.activity_edit_event);

        Toolbar toolbar_main = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getOriginalData();
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

                InputMethodManager imm = (InputMethodManager) ca.bcit.fitmeet.event.EditEventActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
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
    private void getOriginalData(){
        EditText eventName = findViewById(R.id.event_name);
        EditText description = findViewById(R.id.event_description);
        EditText location = findViewById(R.id.event_location);
        EditText tags = findViewById(R.id.event_tags);
        dateTime = findViewById(R.id.event_date);

        String originalEventName = getIntent().getStringExtra("eventName");
        String originalEventLocation = getIntent().getStringExtra("location");
        String originalEventTag = getIntent().getStringExtra("eventTags");
        String originalEventDescription = getIntent().getStringExtra("description");
        String originalEventDateTime= getIntent().getStringExtra("dateTime");
        Log.e("Eventinfo", originalEventName);
        Log.e("Eventinfo", originalEventDescription);
        Log.e("Eventinfo", originalEventLocation);
        Log.e("Eventinfo", originalEventTag);
        Log.e("Eventinfo", originalEventDateTime);

        eventName.setText(originalEventName);
        description.setText(originalEventDescription);
        location.setText(originalEventLocation);
        tags.setText(originalEventTag);
        dateTime.setText(originalEventDateTime);

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
        String tagsString = tags.getText().toString();
        changeInDB(eventNameString, descriptionString, locationString, dateTimeString, tagsString);
    }

    private void changeInDB(String eventNameString, String descriptionString, String locationString, String dateTimeString, String tagsString) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventReference = database.getReference("events");


        eventRef.child("eventName").setValue(eventNameString);
        eventRef.child("eventTag").setValue(tagsString);
        eventRef.child("dateTime").setValue(dateTimeString);
        eventRef.child("location").setValue(locationString);
        eventRef.child("description").setValue(descriptionString);
        finish();

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
        if (dateTimeFragment == null) {
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

}
