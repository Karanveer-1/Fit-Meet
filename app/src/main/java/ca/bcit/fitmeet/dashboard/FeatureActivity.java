package ca.bcit.fitmeet.dashboard;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import ca.bcit.fitmeet.MapFragment;
import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.dashboard.model.Feature;
import ca.bcit.fitmeet.dashboard.model.OLDAFeatures;
import ca.bcit.fitmeet.dashboard.model.PRandCSPFeatures;
import ca.bcit.fitmeet.dashboard.model.SFFeatures;

public class FeatureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar_main = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) { actionBar.setDisplayHomeAsUpEnabled(true); }

        String featureClass = getIntent().getExtras().getString("featureClass");
        String json = getIntent().getExtras().getString("feature");

        Feature feature = null;

        switch (featureClass) {
            case "PRandCSPFeatures":
                SetupPRandCSPFeatures(new Gson().fromJson(json, PRandCSPFeatures.class));
                break;
            case "OLDAFeatures":
                SetupOLDAFeatures(new Gson().fromJson(json, OLDAFeatures.class));
                break;
            case "SFFeatures":
                SetupSFFeatures(new Gson().fromJson(json, SFFeatures.class));
                break;
        }
    }

    private void SetupOLDAFeatures(OLDAFeatures oldaFeatures) {
        setContentView(R.layout.activity_oldafeatures);

        ImageView imageView = findViewById(R.id.collapsable_image);

        int resourceID = getApplication().getResources().getIdentifier(
                "drawable/" + oldaFeatures.getProperties().getImageFileName(), null, getApplication().getPackageName());
        imageView.setImageResource(resourceID);

        String title = oldaFeatures.getProperties().getName();
        ((TextView) findViewById(R.id.feature_title)).setText(title);

        String location = oldaFeatures.getProperties().getStrName();
        String[] split = location.split(",");
        location = split[0] + " " + split[1] + "\n" + split[2].trim() + " " + split[3];
        ((TextView) findViewById(R.id.location_textView)).setText(Format3CommaLocation(location));

        ((TextView) findViewById(R.id.neighbourhood)).setText(oldaFeatures.getProperties().getNeighbourhood());

    }

    private void SetupPRandCSPFeatures(PRandCSPFeatures pRandCSPFeatures) {
        setContentView(R.layout.activity_prandcspfeatures);

        ImageView imageView = findViewById(R.id.collapsable_image);
        /*imageView.setImageResource(
                DashboardCategoryAdapter.locationImage.get(
                        DashboardCategoryAdapter.locationTitle.get(pRandCSPFeatures.getProperties().getName())));
*/
        String title = pRandCSPFeatures.getProperties().getName();
        ((TextView) findViewById(R.id.feature_title)).setText(title);

        ((TextView) findViewById(R.id.location_textView)).setText(pRandCSPFeatures.getProperties().getLocation());
        ((TextView) findViewById(R.id.hours)).setText(pRandCSPFeatures.getProperties().getHours());
        ((TextView) findViewById(R.id.phone)).setText(pRandCSPFeatures.getProperties().getPhone());
        ((TextView) findViewById(R.id.email)).setText(pRandCSPFeatures.getProperties().getEmail());
        ((TextView) findViewById(R.id.website)).setText(pRandCSPFeatures.getProperties().getWebsite());
        /*        ((TextView) findViewById(R.id.description)).setText(pRandCSPFeatures.getProperties().getDescription());*/
    }

    private void SetupSFFeatures(SFFeatures sfFeatures) {
        setContentView(R.layout.activity_sffeatures);

        ImageView imageView = findViewById(R.id.collapsable_image);

        int resourceID = getApplication().getResources().getIdentifier(
                "drawable/" + sfFeatures.getProperties().getImageFileName(), null, getApplication().getPackageName());
        imageView.setImageResource(resourceID);

        String title = sfFeatures.getProperties().getName();
        ((TextView) findViewById(R.id.feature_title)).setText(title);

        String location = sfFeatures.getGeometry().getType();
        ((TextView) findViewById(R.id.location_textView)).setText(Format3CommaLocation(location));

        ((TextView) findViewById(R.id.activities)).setText(sfFeatures.getProperties().getACTIVITIES().replace(";", ", "));

        // pass the latitude and longitude
        setMap();
    }

    private void setMap() {
        Fragment mapFrag = new MapFragment();
        //fill in the location
        ((MapFragment)mapFrag).setLocation(49.283, -123.117);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mapFrag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    private String Format3CommaLocation(String location) {

        String[] split = location.split(",");

        String tempLocation = "";

        try {

            if(split[0].length() < 30) {
                location = split[0] + " " + split[1] + "\n" + split[2].trim() + " " + split[3];
            }
            else {
                location = split[0] + "\n" + split[1].trim() + " " + split[2] + " " + split[3];
            }
        }
        catch(ArrayIndexOutOfBoundsException ex) {

            Log.d("OoB", ex.getMessage());
        }

        return location;
    }
}
