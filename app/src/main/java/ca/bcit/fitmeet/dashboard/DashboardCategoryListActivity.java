package ca.bcit.fitmeet.dashboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.gson.Gson;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.dashboard.model.Category;
import ca.bcit.fitmeet.dashboard.model.CulturalEvents;
import ca.bcit.fitmeet.dashboard.model.OffLeashDogArea;
import ca.bcit.fitmeet.dashboard.model.Parks;
import ca.bcit.fitmeet.dashboard.model.SportsFields;

public class DashboardCategoryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_category_listview);

        String categoryName = getIntent().getExtras().getString("categoryName");
        Category category = GetCategory(categoryName);

        ListView listView = findViewById(R.id.category_listview);

        switch(category.getName()) {

            case "EVENTS":
                listView.setAdapter(new DashboardCategoryEventsAdapter(this, category.getFeatures()));
                break;
        }
    }

    private Category GetCategory(String categoryName) {

        Category category = null;

        String fileName;
        String json;

        switch (categoryName) {
            case "Cultural Events":
                fileName = "EVENTS.json";
                json = FileReader.loadJSONFromAsset(this, fileName);
                category = new Gson().fromJson(json, CulturalEvents.class);
                break;
            case "Off Leash Dog Areas":
                fileName = "OFFLEASH_DOG_AREAS.json";
                json = FileReader.loadJSONFromAsset(this, fileName);
                category = new Gson().fromJson(json, OffLeashDogArea.class);
                break;
            case "Parks":
                fileName = "PARKS.json";
                json = FileReader.loadJSONFromAsset(this, fileName);
                category = new Gson().fromJson(json, Parks.class);
                break;
            case "Sports Fields":
                fileName = "SPORTS_FIELDS.json";
                json = FileReader.loadJSONFromAsset(this, fileName);
                category = new Gson().fromJson(json, SportsFields.class);
                break;
        }

        return category;
    }
}
