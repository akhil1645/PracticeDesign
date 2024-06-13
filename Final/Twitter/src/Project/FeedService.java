package Project;

import java.util.*;
import java.util.Collections;
import java.util.List;

public class FeedService {
    private final Twitter twitter;

    public FeedService(Twitter twitter) {
        this.twitter = twitter;
    }

    public List<Tweet> getFeed(int userId) {
        List<Tweet> feed = new ArrayList<>();
        populateFeed(feed, userId);
        Collections.sort(feed, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet t1, Tweet t2) {
                return t2.getTimestamp().compareTo(t1.getTimestamp());
            }
        });
        return feed;
    }

    private void populateFeed(List<Tweet> feed, int userId) {
        User user = twitter.viewUser(userId);
        if (user != null) {
            Set<Integer> following;
            synchronized (user){ // Synchronize access to the user's following set
                following = new HashSet<>(user.getFollowing());
            }
            for (int followingId : following) {
                User followedUser = twitter.viewUser(followingId);
                if (followedUser != null) {
                    synchronized (followedUser){ // Synchronize access to the followed user's tweets
                        feed.addAll(followedUser.getTweets());
                    }
                }
            }
        }
    }
}
