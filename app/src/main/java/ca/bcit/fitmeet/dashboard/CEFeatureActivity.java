package ca.bcit.fitmeet.dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.dashboard.model.CEFeatures;

public class CEFeatureActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cefeature);

        String json = getIntent().getExtras().getString("ceFeature");
        CEFeatures ceFeature = new Gson().fromJson(json, CEFeatures.class);

        TextView textView = findViewById(R.id.name);
        textView.setText(ceFeature.getProperties().getName());
    }
}
