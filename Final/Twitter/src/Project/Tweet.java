package Project;

import java.util.Date;
import java.util.List;

public interface Tweet {
    int getId();
    int getUserId();
    String getContent();
    Date getTimestamp();
    void like();
    void retweet(int userId,int originalTweetId);
    void addComment(Comment comment);
    List<Integer> getRetweets();
    List<Comment> getComments();
    int getLikes();
}
