package ca.bcit.fitmeet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Intent i = getIntent();
        String text = i.getStringExtra("s");

        TextView tv = findViewById(R.id.textView4);
        tv.setText(text);
    }
}
