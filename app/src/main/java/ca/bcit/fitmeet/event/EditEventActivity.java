package ca.bcit.fitmeet.event;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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
import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipListener;

public class EditEventActivity extends AppCompatActivity {
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private static final int LOCATION_FINDER = 0;
    private static final int PHOTO_SELECTOR = 1;

    private SwitchDateTimeDialogFragment dateTimeFragment;
    private FloatingActionButton saveEvent;
    private EditText dateTime, eventName, description, location, caption;
    private Button uploadButton;
    private ProgressDialog progressDialog;
    private Event originalEvent;

    private Date eventDate;
    private ArrayList<String> tags;
    private String userToken;
    private Uri selectedImage;
    private String coord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseFirebase();
        setContentView(R.layout.activity_edit_event);

        Toolbar toolbar_main = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_main);
        originalEvent = (Event) getIntent().getSerializableExtra("event");
        getOriginalData();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) { actionBar.setDisplayHomeAsUpEnabled(true); }

        initialiseComponents();
        setListener();
    }

    private void getOriginalData() {
        EditText  name = findViewById(R.id.event_name);
        EditText desc = findViewById(R.id.event_description);
        EditText caption = findViewById(R.id.event_caption);
        EditText loc = findViewById(R.id.event_location);
        EditText date = findViewById(R.id.event_date);
        tags = originalEvent.getEventTags();
        eventDate = originalEvent.getDateTime();
        coord = originalEvent.getCoord();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy / hh:mm a", java.util.Locale.getDefault());

        date.setText(dateFormat.format(originalEvent.getDateTime()));
        name.setText(originalEvent.getEventName());
        desc.setText(originalEvent.getDescription());
        caption.setText(originalEvent.getCaption());
        loc.setText(originalEvent.getLocation());
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

    private void initialiseFirebase() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null ) {
            startActivity(new Intent(EditEventActivity.this, LoginActivityMain.class));
            finish();
        } else {
            userToken = currentUser.getUid();
        }
    }

    private void initialiseComponents() {
        initialiseEditTextAndButtons();
        initialiseDateTime();
        initialiseTagCloud();
    }

    private void setListener() {
        setDateTimeListener();
        setLocationListener();
        setSaveEventListener();
        setUploadImageListener();
    }

    private void getEnteredData() {
        TextInputLayout eventNameLayout = findViewById(R.id.name_layout);
        TextInputLayout descriptionLayout = findViewById(R.id.description_layout);
        TextInputLayout captionLayout = findViewById(R.id.caption_layout);

        String eventNameString = eventName.getText().toString().trim();
        String descriptionString = description.getText().toString().trim();
        String locationString = location.getText().toString().trim();
        String dateTimeString = dateTime.getText().toString().trim();
        String captionString = caption.getText().toString().trim();

        boolean error = false;

        if(TextUtils.isEmpty(eventNameString)) {
            error = true;
            eventNameLayout.setError("Please give your event a name!");
        } else {
            eventNameLayout.setError(null);
        }

        if(TextUtils.isEmpty(descriptionString)) {
            error = true;
            descriptionLayout.setError("Don't forget to tell people what it is about!");
        }  else {
            descriptionLayout.setError(null);
        }

        if(TextUtils.isEmpty(captionString)) {
            error = true;
            captionLayout.setError("Give a short caption to standout this event!");
        }  else {
            captionLayout.setError(null);
        }

        if(TextUtils.isEmpty(locationString)) {
            error = true;
            TextView tv = findViewById(R.id.location_error_msg);
            tv.setVisibility(View.VISIBLE);
        } else {
            TextView tv = findViewById(R.id.location_error_msg);
            tv.setVisibility(View.GONE);
        }

        if(TextUtils.isEmpty(dateTimeString)) {
            error = true;
            TextView tv = findViewById(R.id.date_error_msg);
            tv.setVisibility(View.VISIBLE);
        } else {
            TextView tv = findViewById(R.id.date_error_msg);
            tv.setVisibility(View.GONE);
        }

        if(!error) {
            addToDB(eventNameString, descriptionString, locationString, captionString);
        }

    }

    private void addToDB(final String eventNameString, final String descriptionString, final String locationString, final String caption) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();
        final DatabaseReference eventReference = database.getReference("events");

        final String eventID = originalEvent.getEventId();
        final String imageID = originalEvent.getImageReference();

        final Event newEvent = new Event(eventID, userToken,
                eventNameString, descriptionString, locationString, eventDate, tags, imageID, caption, coord);

        if (eventID != null) {
            eventReference.child(eventID).child("eventName").setValue(newEvent.getEventName());
            eventReference.child(eventID).child("description").setValue(newEvent.getDescription());
            eventReference.child(eventID).child("location").setValue(newEvent.getLocation());
            eventReference.child(eventID).child("dateTime").setValue(newEvent.getDateTime());
            eventReference.child(eventID).child("eventTags").setValue(newEvent.getEventTags());
            eventReference.child(eventID).child("caption").setValue(newEvent.getCaption());
            eventReference.child(eventID).child("coord").setValue(newEvent.getCoord());

            if(selectedImage != null) {
                String newID = "image_" + eventID + System.currentTimeMillis();
                newEvent.setImageReference(newID);
                eventReference.child(eventID).child("imageReference").setValue(newEvent.getImageReference());
                StorageReference imageRef = storageRef.child(newID);

                initialiseProgressDialog();
                UploadTask uploadTask = imageRef.putFile(selectedImage);

                uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.incrementProgressBy((int) progress);

                    }
                });

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(EditEventActivity.this, "Got some error. Please try again later!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                });

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(EditEventActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(EditEventActivity.this, EventDetailsActivity.class);
                        i.putExtra("event", newEvent);
                        startActivity(i);
                        finish();
                    }
                });
            } else {
                Intent i = new Intent(EditEventActivity.this, EventDetailsActivity.class);
                i.putExtra("event", newEvent);
                startActivity(i);
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOCATION_FINDER) {
            if (resultCode == RESULT_OK && data != null) {
                String returnString = data.getStringExtra("keyName");
                coord = data.getStringExtra("coord");
                location.setText(returnString);
            }
        } else if (requestCode == PHOTO_SELECTOR) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                selectedImage = data.getData();
                Toast.makeText(EditEventActivity.this,"Upload Image successful",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setUploadImageListener() {
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PHOTO_SELECTOR);
            }
        });
    }

    private void setSaveEventListener() {
        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEnteredData();
            }
        });
    }

    private void setLocationListener() {
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard(view);
                Intent intent = new Intent(EditEventActivity.this, LocationActivity.class);
                startActivityForResult(intent, LOCATION_FINDER);
            }
        });
    }

    private void setDateTimeListener() {
        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimeFragment.startAtCalendarView();
                dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
                hideKeyBoard(view);
            }
        });
    }

    private void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) EditEventActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initialiseEditTextAndButtons() {
        eventName = findViewById(R.id.event_name);
        description = findViewById(R.id.event_description);
        location = findViewById(R.id.event_location);
        dateTime = findViewById(R.id.event_date);
        caption = findViewById(R.id.event_caption);
        saveEvent = findViewById(R.id.save_event);
        uploadButton = findViewById(R.id.btn_upload_image);
        dateTimeFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        dateTime.setInputType(InputType.TYPE_NULL);
    }

    private void initialiseDateTime() {
        if(dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(getString(R.string.date),
                    getString(R.string.ok), getString(R.string.cancel), getString(R.string.clear));
        }

        dateTimeFragment.setTimeZone(TimeZone.getDefault());
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy / hh:mm a", java.util.Locale.getDefault());

        dateTimeFragment.set24HoursMode(false);
        dateTimeFragment.setHighlightAMPMSelection(true);
        dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2018, Calendar.JANUARY, 1).getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2028, Calendar.DECEMBER, 31).getTime());

        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e("Date time", e.getMessage());
        }

        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                eventDate = date;
                dateTime.setText(myDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) { }

            @Override
            public void onNeutralButtonClick(Date date) {
                dateTime.setText(null);
            }
        });
    }


    private void initialiseTagCloud() {
        FlexboxLayout flexbox = findViewById(R.id.flexbox);
        tags = originalEvent.getEventTags();
        ChipCloudConfig config = new ChipCloudConfig()
                .selectMode(ChipCloud.SelectMode.multi)
                .checkedChipColor(Color.parseColor("#ddaa00"))
                .checkedTextColor(Color.parseColor("#ffffff"))
                .uncheckedChipColor(Color.parseColor("#e0e0e0"))
                .uncheckedTextColor(Color.parseColor("#000000"));

        final ChipCloud chipCloud = new ChipCloud(this, flexbox, config);
        String[] demoArray = getResources().getStringArray(R.array.demo_array);
        chipCloud.addChips(demoArray);
        for(int i= 0; i < demoArray.length; i++){
            if(tags.contains(demoArray[i])){
                chipCloud.setChecked(i);
            }
        }
        chipCloud.setListener(new ChipListener() {
            @Override
            public void chipCheckedChange(int index, boolean checked, boolean userClick) {
                if(userClick) {
                    if(checked) {
                        tags.add(chipCloud.getLabel(index));
                    } else {
                        tags.remove(chipCloud.getLabel(index));
                    }
                }
            }
        });
    }

    private void initialiseProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Saving Event!");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
    }
}
