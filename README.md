# SimpleFoodRecommendationEngine
Client.java contains driver code.
RecommendationController implements getRestaurantRecommendations method.
* 1.This initializes sort services that consists of methods to sort Cuisine and Cost lists of user object along with sorting new restaurants according to rating
* 2.Since we always have 3 values for both cuisines and costTracking (1st topmost value is primary, 2nd & 3rd are secondary), SortService uses Heap to get top most used 3 values based on no. of orders.
* 3.SortService also has a method getKNewRestaurantsByRating to get NewReastaurants using heap with comparator getOnboardedTime and rating.
* 4.Once required sorted lists are fetched, derives the restaurant Ids as per mentioned order in problem statement.
* 5.At any point if resultSet reaches MAX_RESULT_SIZE (=100 in given problem), helper methods wont add subsequent values.
* 6.In the code, adding the derived restaurantIds as per sorting to hashset initally so that we can check if a restaturant is already visited or not in O(1) time.
* 7.Converting the hashset to arraylist while returning result.