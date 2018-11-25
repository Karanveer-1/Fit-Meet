package ca.bcit.fitmeet.dashboard;

import ca.bcit.fitmeet.R;

public class DashboardCategory {

    /*public static DashboardCategory CulturalEvents = new DashboardCategory("Cultural Events", R.drawable.dashboard_culturalevents);*/
    public static DashboardCategory OffLeashDogAreas = new DashboardCategory("Off Leash Dog Areas", R.drawable.dashboard_offleashdogarea);
    /*public static DashboardCategory Parks = new DashboardCategory("Parks", R.drawable.dashboard_park);*/
    public static DashboardCategory PRandCSP = new DashboardCategory("Recreation and Community Programs", R.drawable.dashboard_prandcsp);
    public static DashboardCategory SportsFields = new DashboardCategory("Sports Fields", R.drawable.dashboard_sportsfield);


    public final String categoryName;
    public final int drawableId;

    public DashboardCategory (String categoryName, int drawableId) {
        this.categoryName = categoryName;
        this.drawableId = drawableId;
    }
}