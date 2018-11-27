package ca.bcit.fitmeet.event;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.bcit.fitmeet.MainActivity;
import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.SettingsActivity;
import ca.bcit.fitmeet.dashboard.FileReader;
import ca.bcit.fitmeet.dashboard.model.Category;
import ca.bcit.fitmeet.dashboard.model.Feature;
import ca.bcit.fitmeet.dashboard.model.OffLeashDogArea;
import ca.bcit.fitmeet.dashboard.model.PRandCSP;
import ca.bcit.fitmeet.dashboard.model.SportsFields;

public class LocationActivity extends AppCompatActivity {
    private static ArrayList<String> fileName;
    private ArrayList<String> sfLocations;
    private ArrayList<String> PRandCSPLocations;
    private ArrayList<String> OLDLocations;
    private ArrayList<String> sfLocationsCoord;
    private ArrayList<String> PRandCSPLocationsCoord;
    private ArrayList<String> OLDLocationsCoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Toolbar toolbar_main = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        readDataFromJSON();

        List<String> combined = new ArrayList<String>();
        combined.addAll(sfLocations);
        combined.addAll(OLDLocations);
        combined.addAll(PRandCSPLocations);

        ListView list = findViewById(R.id.locations);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, combined);
        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
                String location = tv.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("keyName", location);

                if (sfLocations.contains(location)) {
                    intent.putExtra("loc", "Sports");
                    intent.putExtra("coord", sfLocationsCoord.get(sfLocations.indexOf(location)));
                } else if (OLDLocations.contains(location)) {
                    intent.putExtra("loc", "Animals");
                    intent.putExtra("coord", OLDLocationsCoord.get(OLDLocations.indexOf(location)));
                } else {
                    intent.putExtra("loc", "Recreational");
                    intent.putExtra("coord", PRandCSPLocationsCoord.get(PRandCSPLocations.indexOf(location)));
                }

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void readDataFromJSON() {
        Category category = null;
        String json;

        fileName = new ArrayList<String>(Arrays.asList("OFFLEASH_DOG_AREAS.json",
                "PARKS_RECREATION_AND_COMMUNITY_SCHOOL_PROGRAMMING.json", "SPORTS_FIELDS.json"));

        for (String fn: fileName) {
            json = FileReader.loadJSONFromAsset(this, fn);
            switch (fn) {
                case "OFFLEASH_DOG_AREAS.json":
                    category = new Gson().fromJson(json, OffLeashDogArea.class);
                    readAnimalsFeatures(category);
                    break;
                case "PARKS_RECREATION_AND_COMMUNITY_SCHOOL_PROGRAMMING.json":
                    category = new Gson().fromJson(json, PRandCSP.class);
                    readRecreationFeatures(category);
                    break;
                case "SPORTS_FIELDS.json":
                    category = new Gson().fromJson(json, SportsFields.class);
                    readSportsFieldFeatures(category);
                    break;
            }
        }
    }

    private void readSportsFieldFeatures(Category category) {
        List<Feature> features = category.getFeatures();
        sfLocations = new ArrayList<>();
        sfLocationsCoord = new ArrayList<>();
        for(Feature f:features) {
            sfLocations.add(f.getProperties().getName());
            sfLocationsCoord.add(f.getGeometry().getCoordinates().toString());
        }
    }

    private void readRecreationFeatures(Category category) {
        List<Feature> features = category.getFeatures();
        PRandCSPLocations = new ArrayList<>();
        PRandCSPLocationsCoord = new ArrayList<>();
        for(Feature f:features) {
            PRandCSPLocations.add(f.getProperties().getName());
            PRandCSPLocationsCoord.add(f.getGeometry().getCoordinates().toString());
        }
    }

    private void readAnimalsFeatures(Category category) {
        List<Feature> features = category.getFeatures();
        OLDLocations = new ArrayList<>();
        OLDLocationsCoord = new ArrayList<>();
        for(Feature f:features) {
            OLDLocations.add(f.getProperties().getName());
            OLDLocationsCoord.add(f.getGeometry().getCoordinates().toString());
        }
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


}
