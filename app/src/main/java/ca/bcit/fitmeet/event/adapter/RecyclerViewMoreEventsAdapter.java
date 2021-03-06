package ca.bcit.fitmeet.event.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.List;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.event.EventDetailsActivity;
import ca.bcit.fitmeet.event.model.Event;

public class RecyclerViewMoreEventsAdapter extends RecyclerView.Adapter<RecyclerViewMoreEventsAdapter.moreEventsView> {
    private List<Event> events;
    private Context mContext;

    public RecyclerViewMoreEventsAdapter(List<Event> events, Context mContext) {
        this.events = events;
        this.mContext = mContext;
    }

    @Override
    public RecyclerViewMoreEventsAdapter.moreEventsView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_events_list_row, null);
        RecyclerViewMoreEventsAdapter.moreEventsView singleItemRowHolder = new RecyclerViewMoreEventsAdapter.moreEventsView(v);
        return singleItemRowHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewMoreEventsAdapter.moreEventsView holder, int position) {
        final Event event = events.get(position);

        StorageReference storageReference= FirebaseStorage.getInstance().getReference();;
        final StorageReference ref = storageReference.child(event.getImageReference());

        if (mContext != null) {
            Glide.with(mContext).load(ref).
                    apply(RequestOptions.bitmapTransform(new RoundedCorners(15))).
                    into(holder.image);
        }

        final SimpleDateFormat myDateFormat = new SimpleDateFormat("EEE MMM d, hh:mm a", java.util.Locale.getDefault());

        holder.dateTime.setText(myDateFormat.format(event.getDateTime()));
        holder.title.setText(event.getEventName());
        holder.caption.setText(event.getCaption());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, EventDetailsActivity.class);
                i.putExtra("event", event);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != events ? events.size() : 0);
    }

    public class moreEventsView extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView title;
        protected TextView dateTime;
        protected TextView caption;

        public moreEventsView(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.event_image);
            this.title = itemView.findViewById(R.id.heading);
            this.dateTime = itemView.findViewById(R.id.timing);
            this.caption = itemView.findViewById(R.id.caption);
        }
    }
}
