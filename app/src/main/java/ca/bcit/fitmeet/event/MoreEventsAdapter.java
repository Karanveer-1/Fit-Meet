package ca.bcit.fitmeet.event;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.event.model.Event;

public class MoreEventsAdapter extends ArrayAdapter<Event> {
    Context context;

    public MoreEventsAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.more_events_list_row, parent, false);
        }

        return convertView;
    }
}
