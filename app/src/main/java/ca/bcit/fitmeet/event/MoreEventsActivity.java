package ca.bcit.fitmeet.event;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.event.model.Event;

public class MoreEventsActivity extends AppCompatActivity {
    private static String section = "Section";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_events);

        Toolbar toolbar_main = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String section = intent.getStringExtra("section");

        ListView moreEvents = findViewById(R.id.more_events);
        ArrayList<Event> str = new ArrayList<Event>();
        str.add(new Event());
        str.add(new Event());
        str.add(new Event());
        str.add(new Event());
        MoreEventsAdapter adapter = new MoreEventsAdapter(MoreEventsActivity.this, str);

        moreEvents.setAdapter(adapter);

    }
}
