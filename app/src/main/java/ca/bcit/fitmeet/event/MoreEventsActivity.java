package ca.bcit.fitmeet.event;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.event.adapter.RecyclerViewMoreEventsAdapter;
import ca.bcit.fitmeet.event.model.Event;
import ca.bcit.fitmeet.event.model.EventTagData;

public class MoreEventsActivity extends AppCompatActivity {
    private static final String section = "Section";
    private ArrayList<Event> moreEventsList;
    private RecyclerViewMoreEventsAdapter adapter;
    private String sectionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        sectionType = intent.getStringExtra(section);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events");
        databaseReference.addValueEventListener(listener);

        setContentView(R.layout.activity_more_events);

        Toolbar toolbar_main = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        TextView tv = findViewById(R.id.more_events_heading);
        tv.setText(sectionType);

        moreEventsList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.more_even_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapter = new RecyclerViewMoreEventsAdapter(moreEventsList, this);
        recyclerView.setAdapter(adapter);
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            moreEventsList.clear();

            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Event event = ds.getValue(Event.class);
                switch(sectionType) {
                    case"This Week":
                        fillListWithThisWeekEvents(event);
                        break;
                    case"Outdoors":
                        fillListWithOutdoorEvents(event);
                        break;
                    case"Off-Leash Dog Area":
                        fillListWithAnimalsEvents(event);
                        break;
                    case"Meditation/Yoga":
                        fillListWithRelaxingEvents(event);
                        break;
                    case"Sports":
                        fillListWithSportsEvents(event);
                        break;
                    case"Cultural Events":
                        fillListWithCulturalEvents(event);
                        break;
                    case"Any":
                        moreEventsList.add(event);
                        break;
                }
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

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

    private void fillListWithCulturalEvents(Event event) {
        if (EventTagData.hasCulturalTags(event.getEventTags())) {
            moreEventsList.add(event);
        }
    }

    private void fillListWithSportsEvents(Event event) {
        if (EventTagData.hasSportsTags(event.getEventTags())) {
            moreEventsList.add(event);
        }
    }

    private void fillListWithRelaxingEvents(Event event) {
        if (EventTagData.hasRalxingTags(event.getEventTags())) {
            moreEventsList.add(event);
        }
    }

    private void fillListWithAnimalsEvents(Event event) {
        if (EventTagData.hasAnimalTags(event.getEventTags())) {
            moreEventsList.add(event);
        }
    }

    private void fillListWithOutdoorEvents(Event event) {
        if (EventTagData.hasOutdoorTags(event.getEventTags())) {
            moreEventsList.add(event);
        }
    }

    private void fillListWithThisWeekEvents(Event event) {
        Date monday = getCalender().getTime();
        Date nextMonday= new Date(monday.getTime()+7*24*60*60*1000);
        if (event.getDateTime().after(monday) && event.getDateTime().before(nextMonday)) {
            moreEventsList.add(event);
        }
    }

    private Calendar getCalender() {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

}
