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
import ca.bcit.fitmeet.dashboard.model.Category;
import ca.bcit.fitmeet.dashboard.model.Feature;
import ca.bcit.fitmeet.dashboard.model.OLDAFeatures;
import ca.bcit.fitmeet.dashboard.model.OffLeashDogArea;
import ca.bcit.fitmeet.dashboard.model.PRandCSP;
import ca.bcit.fitmeet.dashboard.model.PRandCSPFeatures;
import ca.bcit.fitmeet.dashboard.model.SFFeatures;
import ca.bcit.fitmeet.dashboard.model.SportsFields;

public class DashboardCategoryAdapter extends ArrayAdapter {

    public static Map<String, Integer> locationImage = new HashMap<>();

    static {
        locationImage.put("feature_queens_park_north_field", R.drawable.feature_queens_park_north_field);
        locationImage.put("feature_queens_park_south_field", R.drawable.feature_queens_park_south_field);
        locationImage.put("feature_grimson_park", R.drawable.feature_grimson_park);
    }

    public static Map<String, String> locationTitle = new HashMap<>();

    static {
        locationTitle.put("Queen's Park - North Field", "feature_queens_park_north_field");
    }

    private Context context;
    private List<Feature> features;
    private Category category;

    public DashboardCategoryAdapter(@NonNull Context context, List<Feature> features, Category category) {
        super(context, 0, features);
        this.context = context;
        this.features = features;
        this.category = category;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        View v = view;

        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.dashboard_category_listview_row, parent, false);
        }

        ImageView picture = v.findViewById(R.id.myImageView);

        final Feature feature = features.get(position);

        picture.setImageResource(R.drawable.parks_glenbrook_ravine);

        if (feature.getProperties().getName().equals("Queen's Park - North Field")) {
            picture.setImageResource(locationImage.get("feature_queens_park_north_field"));
        }

        if (feature.getProperties().getName().equals("Queen's Park - South Field")) {
            picture.setImageResource(locationImage.get("feature_queens_park_south_field"));
        }

        if (feature.getProperties().getName().equals("Grimson Park")) {
            picture.setImageResource(locationImage.get("feature_grimson_park"));
        }

        ((TextView) v.findViewById(R.id.name)).setText(feature.getProperties().getName());

        TextView details = v.findViewById(R.id.details);

        switch (category.getName()) {
            case "OFFLEASH_DOG_AREAS":
                details.setText(((OLDAFeatures) feature).getProperties().getNeighbourhood());
                break;
            case "PARKS_RECREATION_AND_COMMUNITY_SCHOOL_PROGRAMMING":
                details.setText(((PRandCSPFeatures) feature).getProperties().getWebsite());
                break;
            case "SPORTS_FIELDS":
                details.setText(((SFFeatures) feature).getProperties().getACTIVITIES().replace(";", ", "));
                break;
        }


        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ShowFeature(feature);
            }
        });

        return v;
    }

    private void ShowFeature(Feature feature) {

        Intent i = new Intent(getContext(), FeatureActivity.class);
        i.putExtra("featureClass", feature.getClass().getSimpleName());
        i.putExtra("feature", new Gson().toJson(feature));
        context.startActivity(i);
    }
}
