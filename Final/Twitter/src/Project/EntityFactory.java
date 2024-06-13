package Project;

public class EntityFactory {
    private static int nextId = 1;

    public static Tweet createTweet(int userId, String content) {
        return new TweetImpl(nextId++, userId, content);
    }

    public static Comment createComment(int userId, String content) {
        return new CommentImpl(userId, content);
    }

    public static User createUser(String name, String bio, String image) {
        return new UserImpl(nextId++, name, bio, image);
    }
}
