package ca.bcit.fitmeet.event;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.event.model.Event;

public class EventDetailsActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    DatabaseReference userRef;
    DatabaseReference eventRef;
    private FirebaseAuth mAuth;
    private String userToken;
    private String eventId;
    private String hostId;
    FirebaseUser currentUser;
    ArrayList<String> participants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Intent i = getIntent();
        String text = i.getStringExtra("s");
        eventId = i.getStringExtra("eventId");
        hostId = i.getStringExtra("hostId");


        TextView tv = findViewById(R.id.textView4);
        tv.setText(text);

        participants = getParticipants(eventId);
        userRef = FirebaseDatabase.getInstance().getReference("users");
        eventRef = FirebaseDatabase.getInstance().getReference("events");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userToken = currentUser.getUid();



        final Button joinEventButton = findViewById(R.id.joinEventButton);
        joinEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinEvent(eventId, userToken);
            }
        });
        final Button unjoinEventButton = findViewById(R.id.unjoinEventButton);
        unjoinEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unjoinEvent(eventId, userToken);
            }
        });

        final Button editButton  = findViewById(R.id.editEventButton);
        final Button deleteButton = findViewById(R.id.deleteEventButton);
        if(!userToken.equals(hostId)){
            editButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent();
                Intent i = new Intent(EventDetailsActivity.this, MoreEventsActivity.class);

                startActivity(i);
                finish();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventDetailsActivity.this, EditEventActivity.class);

                Event event = (Event) getIntent().getSerializableExtra("event");
                String originalEventName = event.getEventName();
                String originalEventLocation = event.getLocation();
                ArrayList<String> originalEventTag = event.getEventTags();
                String originalEventDescription = event.getDescription();
                Date originalEventDateTime= event.getDateTime();
                i.putExtra("eventId", eventId);
                i.putExtra("location", originalEventLocation);
                i.putExtra("eventName", originalEventName);
                i.putExtra("eventTags", Arrays.asList(originalEventTag).toString());
                i.putExtra("dateTime", originalEventDateTime.toString());
                i.putExtra("description", originalEventDescription);
                startActivity(i);
                finish();
            }
        });
    }

    

    public void deleteEvent(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events").child(eventId);
        ref.setValue(null);
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users");


        //Delete event from users

        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                if (dataSnapshot.exists()) {
                    Log.e("user", dataSnapshot.getKey());
                    String user = dataSnapshot.getKey();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(ds.child("participating").exists()){
                            for (DataSnapshot participatingEvents : ds.child("participating").getChildren()) {
                                Log.e("event", participatingEvents.getKey());
                                if(participatingEvents.getKey().equals(eventId)){
                                    Log.e("Removing", participatingEvents.getKey() + " " + ref.child(user).child("participating").child(participatingEvents.getKey()).getKey());
                                    FirebaseDatabase.getInstance().getReference("users").child(userToken).child("participating").child(participatingEvents.getKey()).setValue(null);

                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
            }
        };

        ref2.addValueEventListener(messageListener);


    }

    //JOINS CURRENT EVENT AS LOGGED IN USER
    public void joinEvent(String eventId, String userId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events").child(eventId).child("participants");
        ref.child(userId).setValue(true);
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users").child(userToken).child("participating");
        ref2.child(eventId).setValue(true);
        Log.e("JOINED", eventId + " : " + userId);
    }

    //JOINS CURRENT EVENT AS LOGGED IN USER
    public void unjoinEvent(String eventId, String userId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events").child(eventId).child("participants");
        ref.child(userId).setValue(null);
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users").child(userToken).child("participating");
        ref2.child(eventId).setValue(null);
        Log.e("UNJOINED", eventId + " : " + userId);
    }


    //GETS PARTICIPANTS OF AN EVENT
    public ArrayList<String> getParticipants(String eventId) {
        final ArrayList<String> participants = new ArrayList<String>();
        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot = dataSnapshot.child("participants");
                if (dataSnapshot.exists()) {
                    Log.e("KEY", dataSnapshot.getKey());
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String component = ds.getKey();
                        if(!participants.contains(component)){
                            participants.add(component);
                        }
                    }
                }
                TextView participantsTextView = findViewById(R.id.participants);
                String s = "";
                for(String a : participants){
                    s += a + "\n";
                }
                String par = participants.toString();
                participantsTextView.setText(par);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
            }
        };
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events").child(eventId);

        ref.addValueEventListener(messageListener);
        return participants;
    }



}