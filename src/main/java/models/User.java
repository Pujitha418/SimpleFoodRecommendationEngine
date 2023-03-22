package models;

import java.util.List;

public class User {
    private List<CuisineTracking> cuisineTrackingList;
    private List<CostTracking> costTrackingList;

    public User() {
    }

    public User(List<CuisineTracking> cuisineTrackingList, List<CostTracking> costTrackingList) {
        this.cuisineTrackingList = cuisineTrackingList;
        this.costTrackingList = costTrackingList;
    }

    public List<CuisineTracking> getCuisineTrackingList() {
        return cuisineTrackingList;
    }

    public void setCuisineTrackingList(List<CuisineTracking> cuisineTrackingList) {
        this.cuisineTrackingList = cuisineTrackingList;
    }

    public List<CostTracking> getCostTrackingList() {
        return costTrackingList;
    }

    public void setCostTrackingList(List<CostTracking> costTrackingList) {
        this.costTrackingList = costTrackingList;
    }
}
