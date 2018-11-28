package ca.bcit.fitmeet.dashboard;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.dashboard.model.Category;
import ca.bcit.fitmeet.dashboard.model.OffLeashDogArea;
import ca.bcit.fitmeet.dashboard.model.PRandCSP;
import ca.bcit.fitmeet.dashboard.model.SportsFields;

public class DashboardCategoryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_category_listview);

        String categoryName = getIntent().getExtras().getString("categoryName");
        Category category = GetCategory(categoryName);

        Toolbar toolbar = findViewById(R.id.listview_toolbar);
        toolbar.setTitle(categoryName);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) { actionBar.setDisplayHomeAsUpEnabled(true); }

        ListView listView = findViewById(R.id.category_listview);
        listView.setAdapter(new DashboardCategoryAdapter(this, category.getFeatures(), category));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Category GetCategory(String categoryName) {

        Category category = null;

        String fileName;
        String json;

        switch (categoryName) {
            /*case "Cultural Events":
                fileName = "EVENTS.json";
                json = FileReader.loadJSONFromAsset(this, fileName);
                category = new Gson().fromJson(json, CulturalEvents.class);
                break;*/
            case "Off Leash Dog Areas":
                fileName = "OFFLEASH_DOG_AREAS.json";
                json = FileReader.loadJSONFromAsset(this, fileName);
                category = new Gson().fromJson(json, OffLeashDogArea.class);
                break;
            /*case "Parks":
                fileName = "PARKS.json";
                json = FileReader.loadJSONFromAsset(this, fileName);
                category = new Gson().fromJson(json, Parks.class);
                break;*/
            case "Recreation and Community Programs":
                fileName = "PARKS_RECREATION_AND_COMMUNITY_SCHOOL_PROGRAMMING.json";
                json = FileReader.loadJSONFromAsset(this, fileName);
                category = new Gson().fromJson(json, PRandCSP.class);
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
