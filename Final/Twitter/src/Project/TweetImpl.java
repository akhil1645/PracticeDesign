package Project;

import java.util.*;

public class TweetImpl implements Tweet {
    private final int id;
    private final int userId;
    private final String content;
    private final Date timestamp;
    private int likes = 0;
    private final List<Comment> comments = new ArrayList<>();
    private final List<Integer> retweets = new ArrayList<>();

    public TweetImpl(int id, int userId, String content) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.timestamp = new Date();
    }

    @Override
    public int getId() { return id; }

    @Override
    public int getUserId() { return userId; }

    @Override
    public String getContent() { return content; }

    @Override
    public Date getTimestamp() { return timestamp; }

    @Override
    public synchronized void like() { likes++; }

    @Override
    public synchronized void retweet(int userId, int originalTweetId) {
        retweets.add(userId);
        // Additional logic can be added here if necessary to handle retweet specifics
    }

    @Override
    public synchronized void addComment(Comment comment) { comments.add(comment); }

    public List<Integer> getRetweets() {
        return retweets;
    }

    public int getLikes() {
        return likes;
    }

    public List<Comment> getComments(){
        return comments;
    }
}