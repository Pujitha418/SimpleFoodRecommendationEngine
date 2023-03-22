# SimpleFoodRecommendationEngine
Client.java contains driver code.
RecommendationController implements getRestaurantRecommendations method.
    - This initializes sort services that consists of methods to sort Cuisine and Cost lists of user object along with sorting new 
        restaurants according to rating
    - Since we always have 3 values for both cuisines and costTracking (1st topmost value is primary, 2nd & 3rd are secondary), SortService uses
        Heap to get top most used 3 values based on no. of orders.
    - SortService also has a method getKNewRestaurantsByRating to get NewReastaurants using heap with comparator getOnboardedTime and rating.
    - Once required sorted lists are fetched, derives the restaurant Ids as per mentioned order in problem statement.
    - At any point if resultSet reaches MAX_RESULT_SIZE (=100 in given problem), helper methods wont add subsequent values.
    - In the code, adding the derived restaurantIds as per sorting to hashset initally so that we can check if a restaturant is already visited
        or not in O(1) time.
    - Converting the hashset to arraylist while returning result.