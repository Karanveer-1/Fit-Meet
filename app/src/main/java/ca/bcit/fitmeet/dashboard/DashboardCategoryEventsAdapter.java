package ca.bcit.fitmeet.dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.dashboard.model.CEFeatures;

public class DashboardCategoryEventsAdapter extends ArrayAdapter {

    private Context context;
    private List<CEFeatures> ceFeatures;

    public DashboardCategoryEventsAdapter(@NonNull Context context, List<CEFeatures> ceFeatures) {
        super(context, 0, ceFeatures);
        this.context = context;
        this.ceFeatures = ceFeatures;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        View v = view;

        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.dashboard_category_listview_row, parent, false);
        }

        ImageView picture = v.findViewById(R.id.myImageView);
        TextView textView = v.findViewById(R.id.name);

        final CEFeatures ceFeature = ceFeatures.get(position);

        picture.setImageResource(R.drawable.parks_glenbrook_ravine);
        textView.setText(ceFeature.getProperties().getName());

        v.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ShowCEFeature(ceFeature);
            }
        });

        return v;
    }

    private void ShowCEFeature(CEFeatures ceFeature) {
        Intent i = new Intent(getContext(), CEFeatureActivity.class);
        i.putExtra("ceFeature", new Gson().toJson(ceFeature));
        context.startActivity(i);
    }
}
