package Project;

import java.util.*;

public class UserImpl implements User {
    private final int id;
    private final String name;
    private final String bio;
    private final String image;
    private final Set<Integer> followers = new HashSet<>();
    private final Set<Integer> following = new HashSet<>();
    private final List<Tweet> tweets = new ArrayList<>();

    public UserImpl(int id, String name, String bio, String image) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.image = image;
    }

    @Override
    public int getId() { return id; }

    @Override
    public String getName() { return name; }

    @Override
    public String getBio() { return bio; }

    @Override
    public String getImage() { return image; }

    @Override
    public Set<Integer> getFollowers() {
        synchronized (followers) {
            return new HashSet<>(followers);
        }
    }

    @Override
    public Set<Integer> getFollowing() {
        synchronized (following) {
            return new HashSet<>(following);
        }
    }

    @Override
    public List<Tweet> getTweets() {
        synchronized (tweets) {
            return new ArrayList<>(tweets);
        }
    }

    @Override
    public void follow(int userId) {
        synchronized (following) {
            following.add(userId);
        }
    }

    @Override
    public void unfollow(int userId) {
        synchronized (following) {
            following.remove(userId);
        }
    }

    @Override
    public void addFollower(int userId) {
        synchronized (followers) {
            followers.add(userId);
        }
    }

    @Override
    public void removeFollower(int userId) {
        synchronized (followers) {
            followers.remove(userId);
        }
    }

    @Override
    public void postTweet(Tweet tweet) {
        synchronized (tweets) {
            tweets.add(tweet);
        }
    }

    @Override
    public void deleteTweet(Tweet tweet) {
        synchronized (tweets) {
            tweets.remove(tweet);
        }
    }
}
