package controllers;

import enums.Cuisine;
import models.CostTracking;
import models.Restaurant;
import models.User;
import services.SortService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class RecommendationController {
    final int MAX_RESULT_SIZE = 100;
    private final Logger logger = Logger.getLogger(RecommendationController.class
            .getClass().getName());
    private Set<String> addFeaturedRestaurants(List<Restaurant> restaurants,
                                               List<Cuisine> cuisines,
                                               List<CostTracking> costTrackings,
                                               int cuisineIdx,
                                               int costIdx,
                                               Set<String> result) {
        int cnt = result.size();
        if (cnt == MAX_RESULT_SIZE) return result;
        if (cuisines.size()<=cuisineIdx || costTrackings.size() <= costIdx) return result;

        for (Restaurant restaurant :
                restaurants) {
            if (restaurant.isRecommended()
                    && restaurant.getCuisine().equals(cuisines.get(cuisineIdx))
                    && restaurant.getCostBracket() == costTrackings.get(costIdx).getType()
                    && !result.contains(restaurant.getRestaurantId())) {
                result.add(restaurant.getRestaurantId());
                cnt += 1;
            }
            if (cnt == MAX_RESULT_SIZE) break;
        }
        return result;
    }

    private Set<String> addNonFeaturedRestaurantsWithRatingGreaterThanOrEqualTo(List<Restaurant> restaurants,
                                                                                List<Cuisine> cuisines,
                                                                                List<CostTracking> costTrackings,
                                                                                int cuisineIdx,
                                                                                int costIdx,
                                                                                float rating,
                                                                                Set<String> result) {
        int cnt = result.size();
        if (cnt == MAX_RESULT_SIZE) return result;
        if (cuisines.size()<=cuisineIdx || costTrackings.size() <= costIdx) return result;

        for (Restaurant restaurant :
                restaurants) {
            if (restaurant.getCuisine().equals(cuisines.get(cuisineIdx))
                    && restaurant.getCostBracket() == costTrackings.get(costIdx).getType()
                    && restaurant.getRating() >= rating
                    && !result.contains(restaurant.getRestaurantId())) {
                result.add(restaurant.getRestaurantId());
                cnt += 1;
            }
            if (cnt == MAX_RESULT_SIZE) break;
        }
        return result;
    }

    private Set<String> addNonFeaturedRestaurantsWithRatingLessThan(List<Restaurant> restaurants,
                                                                    List<Cuisine> cuisines,
                                                                    List<CostTracking> costTrackings,
                                                                    int cuisineIdx,
                                                                    int costIdx,
                                                                    float rating,
                                                                    Set<String> result) {
        int cnt = result.size();
        if (cnt == MAX_RESULT_SIZE) return result;
        if (cuisines.size()<=cuisineIdx || costTrackings.size() <= costIdx) return result;

        for (Restaurant restaurant :
                restaurants) {
            if (restaurant.getCuisine().equals(cuisines.get(cuisineIdx))
                    && restaurant.getCostBracket() == costTrackings.get(costIdx).getType()
                    && restaurant.getRating() < rating
                    && !result.contains(restaurant.getRestaurantId())) {
                result.add(restaurant.getRestaurantId());
                cnt += 1;
            }
            if (cnt == MAX_RESULT_SIZE) break;
        }
        return result;
    }

    private Set<String> addRestaurantsNoCriteria(List<Restaurant> restaurants, Set<String> result) {
        int cnt = result.size();
        if (cnt == MAX_RESULT_SIZE) return result;

        for (Restaurant restaurant :
                restaurants) {
            if (!result.contains(restaurant.getRestaurantId())) {
                result.add(restaurant.getRestaurantId());
                cnt += 1;
            }
            if (cnt == MAX_RESULT_SIZE) break;
        }
        return result;
    }

    public List<String> getRestaurantRecommendations(User user, List<Restaurant> availableRestaurants) {
        SortService sortService = new SortService();
        logger.info("Initialized sortService");

        List<Cuisine> cuisines = sortService.sortCuisines(user.getCuisineTrackingList(), 3);
        List<CostTracking> costTrackings = sortService.sortCost(user.getCostTrackingList(), 3);
        List<Restaurant> newRestaurants = sortService.getKNewRestaurantsByRating(availableRestaurants, 4);

        logger.info("No. of Top cusines - "+cuisines.size());
        logger.info("No. of Top costs - "+costTrackings.size());
        logger.info("No. of Top New Restaurants - "+newRestaurants.size());

        /*for (Cuisine c:
             cuisines) {
            System.out.println("c = " + c);
        }

        for (CostTracking c:
                costTrackings) {
            System.out.println("c = " + +c.getType() + c.getNoOfOrders());
        }

        for (Restaurant c:
                newRestaurants) {
            System.out.println("c = " + c.getOnboardedTime() + c.getRestaurantId());
        }*/


        Set<String> result = new HashSet<>();
        List<String> resultList;

        ///primary cuisine - primary cost featured restaurants
        logger.info("Fetching Featured Restaurants");
        result = addFeaturedRestaurants(availableRestaurants, cuisines, costTrackings, 0, 0, result);
        logger.info("Result Set after primary cost featured restaurants-"+result.size());

        //primary cuisine - secondary cost featured restaurants
        result = addFeaturedRestaurants(availableRestaurants, cuisines, costTrackings, 0, 1, result);
        result = addFeaturedRestaurants(availableRestaurants, cuisines, costTrackings, 0, 2, result);
        logger.info("Result Set after secondary cost featured restaurants-"+result.size());

        //secondary cuisine - primary cost featured restaurants
        result = addFeaturedRestaurants(availableRestaurants, cuisines, costTrackings, 1, 0, result);
        result = addFeaturedRestaurants(availableRestaurants, cuisines, costTrackings, 2, 0, result);
        logger.info("Result Set after primary cost featured restaurants-"+result.size());

        //All restaurants of Primary cuisine, primary cost bracket with rating >= 4
        logger.info("Fetching All restaurants of Primary cuisine, primary cost bracket with rating >= 4");
        result = addNonFeaturedRestaurantsWithRatingGreaterThanOrEqualTo(
                availableRestaurants, cuisines, costTrackings, 0, 0, 4, result);
        logger.info("Result Set after fetch - "+result.size());

        //All restaurants of Primary cuisine, secondary cost bracket with rating >= 4.5
        logger.info("Fetching All restaurants of Primary cuisine, secondary cost bracket with rating >= 4.5");
        result = addNonFeaturedRestaurantsWithRatingGreaterThanOrEqualTo(
                availableRestaurants, cuisines, costTrackings, 0, 1, 4.5F, result);
        result = addNonFeaturedRestaurantsWithRatingGreaterThanOrEqualTo(
                availableRestaurants, cuisines, costTrackings, 0, 2, 4.5F, result);
        logger.info("Result Set after fetch - "+result.size());

        //Top 4 newly created restaurants by rating
        logger.info("Fetching Top 4 newly created restaurants by rating");
        result = addRestaurantsNoCriteria(newRestaurants, result);
        logger.info("Result Set after fetch - "+result.size());

        //All restaurants of Primary cuisine, primary cost bracket with rating < 4
        logger.info("Fetching All restaurants of Primary cuisine, primary cost bracket with rating < 4");
        result = addNonFeaturedRestaurantsWithRatingLessThan(
                availableRestaurants, cuisines, costTrackings, 0, 0, 4, result);
        logger.info("Result Set after fetch - "+result.size());

        //All restaurants of Primary cuisine, secondary cost bracket with rating < 4.5
        logger.info("Fetching All restaurants of Primary cuisine, secondary cost bracket with rating < 4.5");
        result = addNonFeaturedRestaurantsWithRatingLessThan(
                availableRestaurants, cuisines, costTrackings, 0, 1, 4.5F, result);
        result = addNonFeaturedRestaurantsWithRatingLessThan(
                availableRestaurants, cuisines, costTrackings, 0, 2, 4.5F, result);
        logger.info("Result Set after fetch - "+result.size());

        //All restaurants of secondary cuisine, primary cost bracket with rating < 4.5
        logger.info("Fetching All restaurants of secondary cuisine, primary cost bracket with rating < 4.5");
        result = addNonFeaturedRestaurantsWithRatingLessThan(
                availableRestaurants, cuisines, costTrackings, 1, 0, 4.5F, result);
        result = addNonFeaturedRestaurantsWithRatingLessThan(
                availableRestaurants, cuisines, costTrackings, 2, 0, 4.5F, result);
        logger.info("Result Set after fetch - "+result.size());

        logger.info("Fetching remaining restaurants");
        result = addRestaurantsNoCriteria(availableRestaurants, result);
        logger.info("Result Set after fetch - "+result.size());

        resultList = List.copyOf(result);
        logger.info("Converted set to arraylist - "+resultList.size());
        return resultList;
    }
}
