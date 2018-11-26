package ca.bcit.fitmeet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.bcit.fitmeet.dashboard.ProfileEventAdapter;
import ca.bcit.fitmeet.event.model.Event;



public class ProfileEventListFragment extends Fragment {

    private ListView listView;
    private ProfileEventAdapter adapter;
    ArrayList<Event> testDataArray;
    private String userToken;
    private int section;

    public ProfileEventListFragment() {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ProfileEventListFragment newInstance(int sectionNumber) {
        ProfileEventListFragment fragment = new ProfileEventListFragment();
        fragment.setSection(sectionNumber);
        return fragment;
    }

    private void setSection(int sectionNumber) {
        section = sectionNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userToken = FirebaseAuth.getInstance().getUid();
        View rootView = inflater.inflate(R.layout.fragment_profile_event_list2, container, false);
        listView =(ListView) rootView.findViewById(R.id.list_test_data);
        testDataArray = new ArrayList<Event>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        if(section == 1){
            FirebaseDatabase.getInstance().getReference("events").addValueEventListener(listener);
        } else {
            FirebaseDatabase.getInstance().getReference().addValueEventListener(listener2);

        }

        adapter = new ProfileEventAdapter(getActivity(), testDataArray);
        listView.setAdapter(adapter);
        return rootView;
    }



    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Event event = ds.getValue(Event.class);
                if(event.getHostToken().equals(userToken)){
                    testDataArray.add(event);
                }
            }
            adapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

    ValueEventListener listener2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ArrayList<String> eventList = new ArrayList<String>();
            for (DataSnapshot ds : dataSnapshot.child("users").child(userToken).child("participating").getChildren()) {
                if(ds.exists()){
                    Log.e("ADD Ea", ds.getKey());

                    eventList.add(ds.getKey());
                }
            }
            for (DataSnapshot ds : dataSnapshot.child("events").getChildren()) {
                Event event = ds.getValue(Event.class);
                if(eventList.contains(event.getEventId())){
                    Log.e("Added", event.getEventName());
                    Log.e("Added2", event.getLocation());
                    testDataArray.add(event);
                }
            }
            adapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileEventListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileEventListFragment newInstance(String param1, String param2) {
        ProfileEventListFragment fragment = new ProfileEventListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
