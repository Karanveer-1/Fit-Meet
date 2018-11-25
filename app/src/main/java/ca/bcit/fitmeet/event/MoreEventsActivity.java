package ca.bcit.fitmeet.event;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.event.adapter.MoreEventsAdapter;
import ca.bcit.fitmeet.event.model.Event;

public class MoreEventsActivity extends AppCompatActivity {
    private static String section = "Section";
    private ArrayList<Event> moreEventsList;
    private MoreEventsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_events);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events");
        databaseReference.addValueEventListener(listener);

        Toolbar toolbar_main = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String section = intent.getStringExtra("section");
        moreEventsList = new ArrayList<>();
        ListView moreEvents = findViewById(R.id.more_events);
        adapter = new MoreEventsAdapter(MoreEventsActivity.this, moreEventsList);
        moreEvents.setAdapter(adapter);

        moreEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MoreEventsActivity.this, EventDetailsActivity.class);
                intent.putExtra("s", moreEventsList.get(i).toString());
                intent.putExtra("eventId", moreEventsList.get(i).getEventId());
                intent.putExtra("event", moreEventsList.get(i).toString());
                intent.putExtra("hostId", moreEventsList.get(i).getHostToken());
                startActivity(intent);
            }
        });
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Event event = ds.getValue(Event.class);
                moreEventsList.add(event);
            }

            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

}
