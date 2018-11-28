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

        /*mItems.add(DashboardCategory.CulturalEvents);*/
        mItems.add(DashboardCategory.OffLeashDogAreas);
        mItems.add(DashboardCategory.SportsFields);
        mItems.add(DashboardCategory.PRandCSP);
        /*mItems.add(DashboardCategory.Parks);*/
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
    public View getView(int position, View view, ViewGroup viewGroup) {

        View v = view;

        if (v == null) {
            v = mInflater.inflate(R.layout.dashboard_category, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        ImageView picture = (ImageView) v.getTag(R.id.picture);
        TextView name = (TextView) v.getTag(R.id.text);

        final DashboardCategory dashboardCategory = getItem(position);

        picture.setImageResource(dashboardCategory.drawableId);
        name.setText(dashboardCategory.categoryName);

        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ShowCategoryListActivity(dashboardCategory.categoryName);
            }
        });

        return v;
    }

    private void ShowCategoryListActivity(String categoryName) {
        Intent i = new Intent(fragment.getActivity(), DashboardCategoryListActivity.class);
        i.putExtra("categoryName", categoryName);
        fragment.getActivity().startActivity(i);
    }
}