package ca.bcit.fitmeet.dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.dashboard.model.CEFeatures;
import ca.bcit.fitmeet.dashboard.model.Feature;
import ca.bcit.fitmeet.dashboard.model.OLDAFeatures;
import ca.bcit.fitmeet.dashboard.model.PRandCSPFeatures;
import ca.bcit.fitmeet.dashboard.model.SFFeatures;

public class FeatureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String featureClass = getIntent().getExtras().getString("featureClass");
        String json = getIntent().getExtras().getString("feature");

        Feature feature = null;

        switch (featureClass) {
            case "PRandCSPFeatures":
                setContentView(R.layout.activity_prandcspfeatures);
                feature = new Gson().fromJson(json, PRandCSPFeatures.class);
                break;
            case "OLDAFeatures":
                // setContentView(R.layout.activity_oldafeatures);
                feature = new Gson().fromJson(json, OLDAFeatures.class);
                break;
            case "SFFeatures":
                SetupSFFeatures(new Gson().fromJson(json, SFFeatures.class));
                break;
        }
    }

    private void SetupSFFeatures(SFFeatures sfFeatures) {

        setContentView(R.layout.activity_sffeatures);

        ImageView imageView = findViewById(R.id.collapsable_image);
        imageView.setImageResource(
                DashboardCategoryAdapter.locationImage.get(
                        DashboardCategoryAdapter.locationTitle.get(sfFeatures.getProperties().getName())));

        ((TextView) findViewById(R.id.feature_title)).setText(sfFeatures.getProperties().getName());

    }
}
