package ca.bcit.fitmeet.event.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.bcit.fitmeet.event.MoreEventsActivity;
import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.event.model.EventSection;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder>{
    private ArrayList<EventSection> sectionList;
    private Context mContext;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public RecyclerViewDataAdapter(ArrayList<EventSection> sectionList, Context mContext) {
        this.sectionList = sectionList;
        this.mContext = mContext;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_horizontal, null);
        ItemRowHolder rowHolder = new ItemRowHolder(v);

        return rowHolder;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, int position) {
        final String sectionName = sectionList.get(position).getSectionHeading();
        List singleSectionItems = sectionList.get(position).getEvents();
        holder.itemTitle.setText(sectionName);

        SectionListDataAdapter adapter = new SectionListDataAdapter(singleSectionItems, mContext);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(adapter);

        holder.recyclerView.setRecycledViewPool(recycledViewPool);
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, MoreEventsActivity.class));
                Toast.makeText(view.getContext(), "Button More Clicked!" + sectionName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != sectionList ? sectionList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected RecyclerView recyclerView;
        protected Button btnMore;

        public ItemRowHolder(View itemView) {
            super(itemView);
            this.itemTitle = itemView.findViewById(R.id.itemTitle);
            this.recyclerView = itemView.findViewById(R.id.recycler_view_list);
            this.btnMore = itemView.findViewById(R.id.btnMore);
        }
    }
}
