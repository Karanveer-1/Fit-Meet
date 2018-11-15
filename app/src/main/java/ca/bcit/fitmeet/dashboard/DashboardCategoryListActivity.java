package ca.bcit.fitmeet.dashboard;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

public class DashboardCategoryListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String categoryName = getIntent().getExtras().getString("categoryName");

        switch (categoryName) {
            case "Cultural Events":
                break;
            case "Off Leash Dog Areas":
                break;
            case "Parks":
                break;
            case "Sports Fields":
                break;
        }
    }
}
