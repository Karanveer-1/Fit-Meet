package ca.bcit.fitmeet.event;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import ca.bcit.fitmeet.MapFragment;
import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.event.model.Event;
import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipListener;

public class EventDetailsActivity extends AppCompatActivity {
    private Event event;
    private Button join;
    private Button unjoin;
    private String userToken;
    private FirebaseDatabase db;
    private DatabaseReference userRef;
    private DatabaseReference eventRef;

    ArrayList<String> participants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseFirebase();
        Intent i = getIntent();
        event = (Event) i.getSerializableExtra("event");
        initialiseMap();

        setContentView(R.layout.activity_event_details);
        join = findViewById(R.id.join_event);
        unjoin = findViewById(R.id.unjoin_event);

        Toolbar toolbar_main = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        participants = getParticipants(event.getEventId());
        fillValues();
        setListener();
        setHostName();
        disableJoinForHost();

    }

    private void disableJoinForHost() {
        Log.e("Host", userToken + " : " + event.getHostToken());
        if(userToken.equals(event.getHostToken())){
            join.setVisibility(View.GONE);
            unjoin.setVisibility(View.GONE);
            Log.e("visibililty", "set");
        }
    }

    public void setHostName(){
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users").child(event.getHostToken());

        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("HOST", dataSnapshot.getKey());
                String f = "";
                String l = "";
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(ds.getKey().equals("firstName")) {
                            f = ds.getValue(String.class);
                        }
                        if(ds.getKey().equals("lastName")) {
                            l = ds.getValue(String.class);
                        }
                    }
                }
                TextView hostName = findViewById(R.id.hostName);
                hostName.setText(f + " " + l);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };
        ref2.addValueEventListener(messageListener);
    }

    private void initialiseMap() {
        String eventCoor = event.getCoord();
        eventCoor = eventCoor.replace("[","");
        eventCoor = eventCoor.replace("]","");
        String[] coord = eventCoor.split(", ");
        setMap(Double.parseDouble(coord[1]), Double.parseDouble(coord[0]));
    }

    private void setMap(Double lat, Double longi) {
        MapFragment mapFrag = new MapFragment();
        mapFrag.setCoord(lat, longi);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mapFrag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    private void setListener() {
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinEvent(event.getEventId(), userToken);
                participants = getParticipants(event.getEventId());
            }
        });

        unjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unjoinEvent(event.getEventId(), userToken);
                participants = getParticipants(event.getEventId());
            }
        });
    }

    private void initialiseFirebase() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userToken = currentUser.getUid();
        userRef = FirebaseDatabase.getInstance().getReference("users");
        eventRef = FirebaseDatabase.getInstance().getReference("events");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (userToken.equals(event.getHostToken())) {
            getMenuInflater().inflate(R.menu.event_details_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.delete:
                deleteEvent();
                finish();
                return true;
            case R.id.edit:
                Intent i = new Intent(EventDetailsActivity.this, EditEventActivity.class);
                Event event = (Event) getIntent().getSerializableExtra("event");
                String originalEventName = event.getEventName();
                String originalEventLocation = event.getLocation();
                ArrayList<String> originalEventTag = event.getEventTags();
                String originalEventDescription = event.getDescription();
                Date originalEventDateTime= event.getDateTime();
                i.putExtra("event", event);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fillValues() {
        addImage();
        initialiseTagCloud();
        String hostNameString = findUserName();
        TextView name = findViewById(R.id.name);
        TextView caption = findViewById(R.id.caption);
        TextView going = findViewById(R.id.goingcount);
        TextView date = findViewById(R.id.date);
        TextView time = findViewById(R.id.time);
        TextView location = findViewById(R.id.location);
        TextView hostName = findViewById(R.id.hostName);
        TextView description = findViewById(R.id.description);
        join = findViewById(R.id.join_event);
        unjoin = findViewById(R.id.unjoin_event);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMM d, yyyy", java.util.Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", java.util.Locale.getDefault());

        name.setText(event.getEventName());
        caption.setText(event.getCaption());
        going.setText(("Number of Attendees: " + participants.size()));
        date.setText(dateFormat.format(event.getDateTime()));
        time.setText(timeFormat.format(event.getDateTime()));
        location.setText(event.getLocation());
        hostName.setText(hostNameString);
        description.setText(event.getDescription());
    }

    private String findUserName() {
        return "username";
    }

    private void addImage() {
        final ImageView image = findViewById(R.id.event_image);
        StorageReference storageReference= FirebaseStorage.getInstance().getReference();;
        final StorageReference ref = storageReference.child(event.getImageReference());
        Glide.with(EventDetailsActivity.this).
                load(ref).
                apply(RequestOptions.bitmapTransform(new RoundedCorners(15))).
                into(image);
    }


    public void joinEvent(String eventId, String userId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events").child(eventId).child("participants");
        ref.child(userId).setValue(true);
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users").child(userToken).child("participating");
        ref2.child(eventId).setValue(true);
    }

    public void unjoinEvent(String eventId, String userId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events").child(eventId).child("participants");
        ref.child(userId).setValue(null);
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users").child(userToken).child("participating");
        ref2.child(eventId).setValue(null);
    }

    public void deleteEvent(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events").child(event.getEventId());
        ref.setValue(null);
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users");

        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                if (dataSnapshot.exists()) {
                    String user = dataSnapshot.getKey();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(ds.child("participating").exists()){
                            for (DataSnapshot participatingEvents : ds.child("participating").getChildren()) {
                                if(participatingEvents.getKey().equals(event.getEventId())){

                                    FirebaseDatabase.getInstance().getReference("users").
                                            child(userToken).child("participating").child(participatingEvents.getKey()).setValue(null);

                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };
        ref2.addValueEventListener(messageListener);
    }


    public ArrayList<String> getParticipants(String eventId) {

        final ArrayList<String> participants2 = new ArrayList<String>();

        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot = dataSnapshot.child("participants");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String component = ds.getKey();
                        if(!participants2.contains(component)){
                            participants2.add(component);
                        }
                    }
                }
                if(userToken.equals(event.getHostToken())){
                    join.setVisibility(View.GONE);
                    unjoin.setVisibility(View.GONE);
                }else {
                    if (participants2.contains(userToken)) {
                        join.setVisibility(View.GONE);
                        unjoin.setVisibility(View.VISIBLE);
                    } else {
                        join.setVisibility(View.VISIBLE);
                        unjoin.setVisibility(View.GONE);
                    }
                }
                TextView going = findViewById(R.id.goingcount);
                going.setText(("Number of Attendees: " + participants2.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events").child(eventId);
        ref.addValueEventListener(messageListener);
        return participants2;
    }

    private void initialiseTagCloud() {
        FlexboxLayout flexbox = findViewById(R.id.flexbox);

        ChipCloudConfig config = new ChipCloudConfig().selectMode(ChipCloud.SelectMode.none)
                .checkedChipColor(Color.parseColor("#ddaa00"))
                .checkedTextColor(Color.parseColor("#ffffff"));

        final ChipCloud chipCloud = new ChipCloud(this, flexbox, config);

        chipCloud.addChips(event.getEventTags());

        for(int i= 0; i < event.getEventTags().size(); i++){
            chipCloud.setChecked(i);
        }
    }
}
