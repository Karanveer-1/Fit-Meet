package ca.bcit.fitmeet.dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.bcit.fitmeet.R;

public class DashboardAdapter extends BaseAdapter {

    private final ArrayList<DashboardCategory> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private Fragment fragment;

    public DashboardAdapter(Fragment fragment, Context context) {
        this.fragment = fragment;

        mInflater = LayoutInflater.from(context);

        mItems.add(DashboardCategory.CulturalEvents);
        mItems.add(DashboardCategory.OffLeashDogAreas);
        mItems.add(DashboardCategory.Parks);
        mItems.add(DashboardCategory.SportsFields);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public DashboardCategory getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        final int selection = i;

        if (v == null) {
            v = mInflater.inflate(R.layout.dashboard_category, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        final DashboardCategory item = getItem(selection);

        picture.setImageResource(item.drawableId);
        name.setText(item.categoryName);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ShowCategoryListActivity(item.categoryName);
            }
        });

        return v;
    }

    public void ShowCategoryListActivity(String categoryName) {
        Intent i = new Intent(fragment.getActivity(), DashboardCategoryListActivity.class);
        i.putExtra("categoryName", categoryName);
        fragment.getActivity().startActivity(i);
    }
}