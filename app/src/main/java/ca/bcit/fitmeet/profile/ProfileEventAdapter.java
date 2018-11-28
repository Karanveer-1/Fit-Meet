package ca.bcit.fitmeet.profile;

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

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.event.model.Event;
import ca.bcit.fitmeet.event.model.GlideApp;

public class ProfileEventAdapter extends ArrayAdapter<Event> {
    private Context mContext;
    private List<Event> eventList;

    public ProfileEventAdapter(@NonNull Context context, ArrayList<Event> list) {
        super(context, 0, list);
        mContext = context;
        eventList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.more_events_list_row, parent, false);
        }

        Event currEvent = eventList.get(position);

        ImageView image = listItem.findViewById(R.id.event_image);

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(currEvent.getImageReference());

        GlideApp.with(listItem).
                load(storageReference).
                apply(RequestOptions.bitmapTransform(new RoundedCorners(15))).into(image);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy, hh:mm a", java.util.Locale.getDefault());

        TextView name = listItem.findViewById(R.id.heading);
        name.setText(currEvent.getEventName());

        TextView location = listItem.findViewById(R.id.caption);
        location.setText(currEvent.getLocation());

        TextView date =  listItem.findViewById(R.id.timing);
        date.setText(dateFormat.format(currEvent.getDateTime()));

        return listItem;
    }

}
