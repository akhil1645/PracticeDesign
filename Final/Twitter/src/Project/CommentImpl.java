package Project;

public class CommentImpl implements Comment {
    private final int userId;
    private final String content;

    public CommentImpl(int userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    @Override
    public int getUserId() { return userId; }

    @Override
    public String getContent() { return content; }
}
