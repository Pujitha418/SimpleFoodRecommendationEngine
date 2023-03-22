import controllers.RecommendationController;
import enums.Cuisine;
import models.CostTracking;
import models.CuisineTracking;
import models.Restaurant;
import models.User;
import java.util.*;

public class Client {
    //Driver code
    public static void main(String[] args) {
        RecommendationController controller = new RecommendationController();

        System.out.println("Setting up test data");
        List<Restaurant> restaurants = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Restaurant r1 = new Restaurant();
            r1.setRestaurantId("R"+i);
            r1.setCuisine(Cuisine.SouthIndian);
            r1.setCostBracket(40);
            r1.setRating(i%2==0?4.5F:4);
            r1.setRecommended(i%2==0?Boolean.FALSE:Boolean.TRUE);
            r1.setOnboardedTime(new Date());
            restaurants.add(r1);
        }

        for (int i = 100; i < 200; i++) {
            Restaurant r1 = new Restaurant();
            r1.setRestaurantId("R"+i);
            r1.setCuisine(Cuisine.NorthIndian);
            r1.setCostBracket(i*10);
            r1.setRating(i%2==0?4.5F:4);
            r1.setRecommended(i%2==0?Boolean.FALSE:Boolean.TRUE);
            r1.setOnboardedTime(new Date());
            restaurants.add(r1);
        }

        for (int i = 20; i < 45; i++) {
            Restaurant r1 = new Restaurant();
            r1.setRestaurantId("R"+i);
            r1.setCuisine(Cuisine.Chinese);
            r1.setCostBracket(i*10);
            r1.setRating(i%2==0?4.5F:4);
            r1.setRecommended(i%2==0?Boolean.FALSE:Boolean.TRUE);
            //Date date = java.sql.Date.valueOf(LocalDate.parse("2015-02-20"));
            r1.setOnboardedTime(new Date());
            restaurants.add(r1);
        }

        User user = new User();
        List<CuisineTracking> cuisineTrackingList = new ArrayList<>();
        cuisineTrackingList.add(new CuisineTracking(Cuisine.SouthIndian, 2));
        cuisineTrackingList.add(new CuisineTracking(Cuisine.NorthIndian, 3));
        cuisineTrackingList.add(new CuisineTracking(Cuisine.Chinese, 1));
        user.setCuisineTrackingList(cuisineTrackingList);
        List<CostTracking> costTrackingList = new ArrayList<>();
        costTrackingList.add(new CostTracking(40, 20));
        costTrackingList.add(new CostTracking(50, 5));
        costTrackingList.add(new CostTracking(30, 15));
        user.setCostTrackingList(costTrackingList);

        System.out.println("Calling getRestaurantRecommendations");
        List<String> recommendations = controller.getRestaurantRecommendations(user, restaurants);
        System.out.println("recommendations = " + recommendations);

    }


}
