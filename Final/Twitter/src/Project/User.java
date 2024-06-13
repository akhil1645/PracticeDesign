package Project;

import java.util.*;

public interface User {
    int getId();
    String getName();
    String getBio();
    String getImage();
    Set<Integer> getFollowers();
    Set<Integer> getFollowing();
    List<Tweet> getTweets();
    void follow(int userId);
    void unfollow(int userId);
    void addFollower(int userId);
    void removeFollower(int userId);
    void postTweet(Tweet tweet);
    void deleteTweet(Tweet tweet);
}
