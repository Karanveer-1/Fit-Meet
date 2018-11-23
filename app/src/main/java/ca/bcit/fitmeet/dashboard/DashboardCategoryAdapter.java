package ca.bcit.fitmeet.dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.dashboard.model.Feature;

public class DashboardCategoryAdapter extends ArrayAdapter {

    public static Map<String, Integer> locationImage = new HashMap<>();

    static {
        locationImage.put("feature_festival_of_volunteers", R.drawable.feature_festival_of_volunteers);
        locationImage.put("feature_kozak", R.drawable.feature_kozak);
        locationImage.put("feature_lester_event_management", R.drawable.feature_lester_event_management);
    }

    private Context context;
    private List<Feature> features;

    public DashboardCategoryAdapter(@NonNull Context context, List<Feature> features) {
        super(context, 0, features);
        this.context = context;
        this.features = features;
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

        final Feature feature = features.get(position);

        picture.setImageResource(R.drawable.parks_glenbrook_ravine);

        if (feature.getProperties().getName().equals("New Westminster Festival of Volunteers")) {
            picture.setImageResource(locationImage.get("feature_festival_of_volunteers"));
        }

        if (feature.getProperties().getName().equals("W. Ruth Kozak (WAVES WRITERS)")) {
            picture.setImageResource(locationImage.get("feature_kozak"));
        }

        if (feature.getProperties().getName().equals("Lester Event Management")) {
            picture.setImageResource(locationImage.get("feature_lester_event_management"));
        }

        textView.setText(feature.getProperties().getName());

        v.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ShowFeature(feature);
            }
        });

        return v;
    }

    private void ShowFeature(Feature feature) {

        Intent i = new Intent(getContext(), FeatureActivity.class);
        i.putExtra("feature", new Gson().toJson(feature));
        i.putExtra("featureClass", feature.getClass().getSimpleName());
        context.startActivity(i);
    }
}
