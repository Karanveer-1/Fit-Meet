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
import ca.bcit.fitmeet.event.model.EventTagData;

public class EventListFragment extends Fragment implements View.OnClickListener {
    private static final int LIST_SIZE = 10;
    private boolean isFabOpen = false;
    private FloatingActionButton fab,fab1;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private RecyclerViewDataAdapter adapter;

    private ArrayList<EventSection> eventSections;
    ArrayList<Event> comingSoon = new ArrayList<Event>();
    ArrayList<Event> outdoors = new ArrayList<Event>();
    ArrayList<Event> relaxing = new ArrayList<Event>();
    ArrayList<Event> OffLeashDogArea = new ArrayList<Event>();
    ArrayList<Event> sports = new ArrayList<Event>();
    ArrayList<Event> cultural= new ArrayList<Event>();
    ArrayList<Event> other = new ArrayList<Event>();

    public EventListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userToken = currentUser.getUid();
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events");
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
        EventTagData.init();

        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
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
            clearList();

            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Event event = ds.getValue(Event.class);
                Date monday = getCalender().getTime();
                Date nextMonday= new Date(monday.getTime()+7*24*60*60*1000);

                if (event.getDateTime().after(monday) && event.getDateTime().before(nextMonday) && comingSoon.size() < LIST_SIZE) {
                    comingSoon.add(event);
                }else if (EventTagData.hasOutdoorTags(event.getEventTags()) && outdoors.size() < LIST_SIZE) {
                    outdoors.add(event);
                }else if (EventTagData.hasRalxingTags(event.getEventTags()) && outdoors.size() < LIST_SIZE) {
                    relaxing.add( event);
                }else if (EventTagData.hasAnimalTags(event.getEventTags()) && outdoors.size() < LIST_SIZE) {
                    OffLeashDogArea.add(event);
                } else if(EventTagData.hasSportsTags(event.getEventTags()) && outdoors.size() < LIST_SIZE) {
                    sports.add(event);
                } else if(EventTagData.hasCulturalTags(event.getEventTags()) && outdoors.size() < LIST_SIZE) {
                    cultural.add(event);
                } else if(other.size() < LIST_SIZE) {
                    other.add(event);
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
        EventSection comingSoonSection = new EventSection();
        EventSection outdoorSection = new EventSection();
        EventSection meditationSection = new EventSection();
        EventSection animalsSection = new EventSection();
        EventSection sportsSection = new EventSection();
        EventSection cultureSection = new EventSection();
        EventSection otherSection = new EventSection();

        comingSoonSection.setSectionHeading("This Week");
        outdoorSection.setSectionHeading("Outdoors");
        animalsSection.setSectionHeading("Off-Leash Dog Area");
        meditationSection.setSectionHeading("Meditation/Yoga");
        sportsSection.setSectionHeading("Sports");
        cultureSection.setSectionHeading("Cultural Events");
        otherSection.setSectionHeading("Any");


        comingSoonSection.setEvents(comingSoon);
        outdoorSection.setEvents(outdoors);
        animalsSection.setEvents(OffLeashDogArea);
        meditationSection.setEvents(relaxing);
        sportsSection.setEvents(sports);
        cultureSection.setEvents(cultural);
        otherSection.setEvents(other);

        eventSections.add(comingSoonSection);
        eventSections.add(outdoorSection);
        eventSections.add(animalsSection);
        eventSections.add(meditationSection);
        eventSections.add(sportsSection);
        eventSections.add(cultureSection);
        eventSections.add(otherSection);
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

    private void clearList() {
        comingSoon.clear();
        outdoors.clear();
        OffLeashDogArea.clear();
        relaxing.clear();
        sports.clear();
        cultural.clear();
        other.clear();
    }


}
