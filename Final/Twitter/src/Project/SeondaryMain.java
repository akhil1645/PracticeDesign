package Project;

public class SeondaryMain {
    public static void main(String[] args) {
        Twitter twitter = Twitter.getInstance();

        // Adding users
        User user1 = twitter.addUser("User1", "Bio1", "Image1");
        User user2 = twitter.addUser("User2", "Bio2", "Image2");
        User user3 = twitter.addUser("User3", "Bio3", "Image3");

        // User1 posts a tweet
        twitter.postTweet(user1.getId(), "Hello World!");

        // User2 likes User1's tweet
        int tweetId = user1.getTweets().get(0).getId();
        twitter.likeTweet(user2.getId(), tweetId);

        // User2 retweets User1's tweet
        twitter.retweet(user2.getId(), tweetId);

        // User3 comments on User1's tweet
        twitter.commentOnTweet(user3.getId(), tweetId, "Nice tweet!");

        // Display User1's tweets
        displayUserTweets(user1);

        // Concurrency Test
        Thread thread1 = new Thread(() -> {
            twitter.likeTweet(user2.getId(), tweetId);
            twitter.retweet(user3.getId(), tweetId);
        });

        Thread thread2 = new Thread(() -> {
            twitter.likeTweet(user3.getId(), tweetId);
            twitter.commentOnTweet(user2.getId(), tweetId, "Great!");
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display User1's tweets after concurrency operations
        displayUserTweets(user1);
    }

    private static void displayUserTweets(User user) {
        System.out.println("Tweets by " + user.getName() + ":");
        for (Tweet tweet : user.getTweets()) {
            System.out.println("Tweet: " + tweet.getContent());
            System.out.println("Likes: " + tweet.getLikes());
            System.out.println("Comments: ");
            for (Comment comment : tweet.getComments()) {
                System.out.println(" - " + comment.getContent());
            }
            System.out.println();
        }
    }
}
