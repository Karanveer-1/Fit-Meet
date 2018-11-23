package ca.bcit.fitmeet.dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.dashboard.model.CEFeatures;
import ca.bcit.fitmeet.dashboard.model.Feature;
import ca.bcit.fitmeet.dashboard.model.OLDAFeatures;
import ca.bcit.fitmeet.dashboard.model.PFeatures;
import ca.bcit.fitmeet.dashboard.model.PRandCSPFeatures;
import ca.bcit.fitmeet.dashboard.model.SFFeatures;

public class FeatureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);

        String featureClass = getIntent().getExtras().getString("featureClass");
        String json = getIntent().getExtras().getString("feature");

        Feature feature = null;

        switch (featureClass) {
            case "CEFeatures":
                feature = new Gson().fromJson(json, CEFeatures.class);
                break;
            case "PRandCSPFeatures":
                feature = new Gson().fromJson(json, PRandCSPFeatures.class);
                break;
            case "OLDAFeatures":
                feature = new Gson().fromJson(json, OLDAFeatures.class);
                break;
            case "SFFeatures":
                feature = new Gson().fromJson(json, SFFeatures.class);
                break;
        }

        TextView textView = findViewById(R.id.name);
        textView.setText(feature.getProperties().getName());
    }
}
