package services;

import enums.Cuisine;
import models.CostTracking;
import models.CuisineTracking;
import models.Restaurant;

import java.util.*;

public class SortService {
    public List<Cuisine> sortCuisines(List<CuisineTracking> cuisineTrackingList, int resultSize) {
        PriorityQueue<CuisineTracking> minHeap = new PriorityQueue<>(Comparator.comparingInt(CuisineTracking::getNoOfOrders));
        List<Cuisine> topKCusinies = new ArrayList<>();

        for (CuisineTracking cuisine:
             cuisineTrackingList) {
            minHeap.offer(cuisine);

            while (minHeap.size() > resultSize) {
                minHeap.poll();
            }
        }

        while (!minHeap.isEmpty()) {
            topKCusinies.add(minHeap.poll().getType());
        }

        Collections.reverse(topKCusinies);
        return topKCusinies;
    }

    public List<CostTracking> sortCost(List<CostTracking> costTrackingList, int resultSize) {
        PriorityQueue<CostTracking> minHeap = new PriorityQueue<>(Comparator.comparingInt(CostTracking::getNoOfOrders));
        List<CostTracking> topKCosts = new ArrayList<>();

        for (CostTracking costTracking:
                costTrackingList) {
            minHeap.offer(costTracking);

            while (minHeap.size() >= resultSize) {
                minHeap.poll();
            }
        }

        while (!minHeap.isEmpty()) {
            topKCosts.add(minHeap.poll());
        }

        Collections.reverse(topKCosts);
        return topKCosts;
    }

    public List<Restaurant> getKNewRestaurantsByRating(List<Restaurant> restaurants, int resultSize) {
        PriorityQueue<Restaurant> minHeap = new PriorityQueue<>((first, second) -> (int) (first.getRating()-second.getRating()) & first.getOnboardedTime().compareTo(second.getOnboardedTime()));
                //(Comparator.comparing(Restaurant::getOnboardedTime, Restaurant::getRating*-1));
        List<Restaurant> topKRestaurants = new ArrayList<>();

        for (Restaurant restaurant:
                restaurants) {
            minHeap.offer(restaurant);

            while (minHeap.size() >= resultSize) {
                minHeap.poll();
            }
        }

        while (!minHeap.isEmpty()) {
            topKRestaurants.add(minHeap.poll());
        }

        Collections.reverse(topKRestaurants);
        return topKRestaurants;
    }
}
