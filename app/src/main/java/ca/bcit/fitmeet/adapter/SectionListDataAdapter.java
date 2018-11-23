package ca.bcit.fitmeet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.bcit.fitmeet.event.EventDetailsActivity;
import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.event.model.Event;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder>{
    private List<Event> events;
    private Context mContext;

    public SectionListDataAdapter(List<Event> events, Context mContext) {
        this.events = events;
        this.mContext = mContext;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_horizontal_single_item, null);
        SingleItemRowHolder singleItemRowHolder = new SingleItemRowHolder(v);
        return singleItemRowHolder;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int position) {
        final Event event = events.get(position);
        holder.title.setText(event.getEventName());
        holder.dateTime.setText(event.getDateTime().toString());
        holder.caption.setText(event.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, EventDetailsActivity.class);
                i.putExtra("s", event.toString());
                i.putExtra("eventId", event.getEventId());
                i.putExtra("hostId", event.getHostToken());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != events ? events.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView title;
        protected TextView dateTime;
        protected TextView caption;

        public SingleItemRowHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.event_image);
            this.title = itemView.findViewById(R.id.event_title);
            this.dateTime = itemView.findViewById(R.id.event_datetime);
            this.caption = itemView.findViewById(R.id.event_caption);
        }
    }

}
