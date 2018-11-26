package ca.bcit.fitmeet.dashboard;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.event.model.Event;
import ca.bcit.fitmeet.event.model.GlideApp;

public class ProfileEventAdapter extends ArrayAdapter<Event> {

    private Context mContext;
    private List<Event> eventList = new ArrayList<>();

    public ProfileEventAdapter(@NonNull Context context, ArrayList<Event> list) {
        super(context, 0, list);
        mContext = context;
        eventList = list;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.fragment_profile_event_list,parent,false);

        Event currEvent = eventList.get(position);

        ImageView image = (ImageView) listItem.findViewById(R.id.profile_event_image);
        //image.setImageResource(currEvent.getImageReference());
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(currEvent.getImageReference());
        Log.e("load", "loading");
        GlideApp.with(listItem)
                .load(storageReference)
                .into(image);
TextView name = (TextView) listItem.findViewById(R.id.profile_event_name);
        name.setText(currEvent.getEventName());

        TextView location = (TextView) listItem.findViewById(R.id.profile_event_location);
        location.setText(currEvent.getLocation());

        TextView release = (TextView) listItem.findViewById(R.id.profile_event_date);
        release.setText(currEvent.getDateTime().toString());

        return listItem;
    }
}
