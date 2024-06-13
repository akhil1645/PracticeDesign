package Project;

import java.util.*;

public class Twitter {
    private static Twitter instance = null;
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Tweet> tweets = new HashMap<>();
    private final FeedService feedService;

    private Twitter() {
        feedService = new FeedService(this);
    }

    public static synchronized Twitter getInstance() {
        if (instance == null) {
            instance = new Twitter();
        }
        return instance;
    }

    public User addUser(String name, String bio, String image) {
        User user = EntityFactory.createUser(name, bio, image);
        synchronized (users) {
            users.put(user.getId(), user);
        }
        return user;
    }

    public void postTweet(int userId, String content) {
        User user = users.get(userId);
        if (user != null) {
            Tweet tweet = EntityFactory.createTweet(userId, content);
            synchronized (tweets) {
                tweets.put(tweet.getId(), tweet);
            }
            user.postTweet(tweet);
        }
    }

    public void deleteTweet(int userId, int tweetId) {
        User user = users.get(userId);
        if (user != null) {
            Tweet tweet = tweets.get(tweetId);
            if (tweet != null && tweet.getUserId() == userId) {
                user.deleteTweet(tweet);
                synchronized (tweets) {
                    tweets.remove(tweetId);
                }
            }
        }
    }

    public void follow(int userId, int followUserId) {
        User user = users.get(userId);
        User followUser = users.get(followUserId);
        if (user != null && followUser != null) {
            user.follow(followUserId);
            followUser.addFollower(userId);
        }
    }

    public void unfollow(int userId, int unfollowUserId) {
        User user = users.get(userId);
        User unfollowUser = users.get(unfollowUserId);
        if (user != null && unfollowUser != null) {
            user.unfollow(unfollowUserId);
            unfollowUser.removeFollower(userId);
        }
    }

    public void likeTweet(int userId, int tweetId) {
        Tweet tweet = tweets.get(tweetId);
        if (tweet != null) {
            tweet.like();
        }
    }

    public void retweet(int userId, int tweetId) {
        Tweet originalTweet = tweets.get(tweetId);
        if (originalTweet != null) {
            originalTweet.retweet(userId, tweetId);
            Tweet retweet = EntityFactory.createTweet(userId, "ReTweet: " + originalTweet.getContent());
            synchronized (tweets) {
                tweets.put(retweet.getId(), retweet);
            }
            User user = users.get(userId);
            if (user != null) {
                synchronized (user) {
                    user.postTweet(retweet);
                }
            }
        }
    }

    public void commentOnTweet(int userId, int tweetId, String content) {
        Tweet tweet = tweets.get(tweetId);
        if (tweet != null) {
            Comment comment = EntityFactory.createComment(userId, content);
            synchronized (tweet){
                tweet.addComment(comment);
            }
        }
    }

    public User viewUser(int userId) {
        synchronized (users) {
            return users.get(userId);
        }
    }

    public List<Tweet> viewFeed(int userId) {
        return feedService.getFeed(userId);
    }
}