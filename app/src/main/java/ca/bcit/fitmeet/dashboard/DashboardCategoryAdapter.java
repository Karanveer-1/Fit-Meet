package ca.bcit.fitmeet.dashboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DashboardCategoryAdapter extends ArrayAdapter {


    public DashboardCategoryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        View v = view;
        ImageView picture;
        TextView textView;
        TextView textView1;

        return view;
    }
}
