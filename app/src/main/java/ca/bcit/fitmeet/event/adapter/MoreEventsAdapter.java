package ca.bcit.fitmeet.event.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
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

        Event event = getItem(position);
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("EEE MMM d, hh:mm a", java.util.Locale.getDefault());

        final ImageView image = convertView.findViewById(R.id.event_image);
        TextView name = convertView.findViewById(R.id.heading);
        TextView caption = convertView.findViewById(R.id.caption);
        TextView timing = convertView.findViewById(R.id.timing);

        StorageReference storageReference= FirebaseStorage.getInstance().getReference();;

        final StorageReference ref = storageReference.child(event.getImageReference());

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Uri downloadUrl = uri;
                Glide.with(context).load(downloadUrl).
                        apply(RequestOptions.bitmapTransform(new RoundedCorners(15))).into(image);
            }
        });

        name.setText(event.getEventName());
        caption.setText(event.getCaption());
        timing.setText(myDateFormat.format(event.getDateTime()));

        return convertView;
    }
}
