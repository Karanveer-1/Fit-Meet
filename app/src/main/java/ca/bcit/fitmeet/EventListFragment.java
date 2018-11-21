package ca.bcit.fitmeet;


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
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import ca.bcit.fitmeet.adapter.RecyclerViewDataAdapter;
import ca.bcit.fitmeet.model.SectionEventModel;
import ca.bcit.fitmeet.model.SingleEventModel;

public class EventListFragment extends Fragment {

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private RecyclerView recyclerView;
    private Button create;


    public EventListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        allSampleData = new ArrayList<>();
        create = view.findViewById(R.id.createEvent);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateEventActivity.class));
            }
        });

        createDummyData();

        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(allSampleData, getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<SectionEventModel> allSampleData;

    private void createDummyData() {
        for (int i = 1; i <= 20; i++) {
            SectionEventModel dm = new SectionEventModel();
            dm.setSectionHeading("Section " + i);
            ArrayList<SingleEventModel> singleItemModels = new ArrayList<>();
            for (int j = 1; j <= 10; j++) {
                singleItemModels.add(new SingleEventModel("heading", "discreption","datatime"));
            }
            dm.setEvent(singleItemModels);
            allSampleData.add(dm);
        }

    }






















}
