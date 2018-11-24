package ca.bcit.fitmeet.event;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.TestDateActivity;
import ca.bcit.fitmeet.event.adapter.RecyclerViewDataAdapter;
import ca.bcit.fitmeet.event.model.Event;
import ca.bcit.fitmeet.event.model.EventSection;

public class EventListFragment extends Fragment implements View.OnClickListener {
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private FirebaseAuth mAuth;
    private String userToken;
    DatabaseReference databaseReference;

    private RecyclerView recyclerView;
    private ArrayList<EventSection> eventSections;
    RecyclerViewDataAdapter adapter;

    ArrayList<Event> recent = new ArrayList<>();
    ArrayList<Event> animals = new ArrayList<>();
    ArrayList<Event> running = new ArrayList<>();
    ArrayList<Event> meditation = new ArrayList<>();

    public EventListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userToken = currentUser.getUid();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("events");
        databaseReference.addValueEventListener(listener);

        eventSections = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initialiseFabButtons(view);
        loadAnimations();
        setListeners();

        //createDummyData();

        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewDataAdapter(eventSections, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void setListeners() {
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
    }

    private void initialiseFabButtons(View view) {
        fab = view.findViewById(R.id.fab);
        fab1 = view.findViewById(R.id.fab1);
    }

    private void loadAnimations() {
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_backward);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:
                startActivity(new Intent(getActivity(),CreateEventActivity.class));
                animateFAB();
                break;
        }
    }

     public void animateFAB(){
        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab1.setClickable(false);
            isFabOpen = false;
        } else {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab1.setClickable(true);
            isFabOpen = true;
        }
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            recent.clear();
            animals.clear();
            meditation.clear();
            running.clear();

            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Event event = ds.getValue(Event.class);
                Calendar c = Calendar.getInstance();
                c.setFirstDayOfWeek(Calendar.MONDAY);
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                Date monday = c.getTime();
                Date nextMonday= new Date(monday.getTime()+7*24*60*60*1000);

                if (event.getDateTime().after(monday) && event.getDateTime().before(nextMonday)) {
                    recent.add(event);
                }
                if (event.getEventTags().contains("Running")) {
                    running.add(event);
                }
                if (event.getEventTags().contains("Meditation")) {
                    meditation.add( event);
                }
                if (event.getEventTags().contains("Animals")) {
                    animals.add(event);
                }
            }
            populateSections();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

    private void populateSections() {
        eventSections.clear();
        EventSection recentEvents = new EventSection();
        EventSection runningSec = new EventSection();
        EventSection meditationSec = new EventSection();
        EventSection animalsSec = new EventSection();

        recentEvents.setSectionHeading("This Week");
        runningSec.setSectionHeading("Running");
        meditationSec.setSectionHeading("Meditation");
        animalsSec.setSectionHeading("Animals");

        recentEvents.setEvents(recent);
        runningSec.setEvents(running);
        meditationSec.setEvents(meditation);
        animalsSec.setEvents(animals);

        eventSections.add(recentEvents);
        eventSections.add(runningSec);
        eventSections.add(meditationSec);
        eventSections.add(animalsSec);
    }


//    private void createDummyData() {
//        for (int i = 1; i <= 3; i++) {
//            EventSection dm = new EventSection();
//            dm.setSectionHeading("Section " + i);
//            for (int j = 1; j <= 7; j++) {
//                events.add(new Event());
//            }
//            dm.setEvents(events);
//            eventSections.add(dm);
//        }
//    }

















    //READ
//    public ArrayList<Event> retrieve() {
//
//        dref.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                fetchData(dataSnapshot);
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        return events;
//    }
//
//    private void fetchData(DataSnapshot dataSnapshot) {
//        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//            Event event = ds.getValue(Event.class);
//            events.add(event);
//        }
//    }


}
