package Project;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Twitter twitter = Twitter.getInstance();

        // Creating users
        User user1 = twitter.addUser("Alice", "Bio of Alice", "alice.jpg");
        User user2 = twitter.addUser("Bob", "Bio of Bob", "bob.jpg");
        User user3 = twitter.addUser("Charlie", "Bio of Charlie", "charlie.jpg");

        // Creating threads to simulate concurrent actions
        Thread thread1 = new Thread(() -> {
            twitter.postTweet(user1.getId(), "Hello from Alice!");
            twitter.follow(user1.getId(), user2.getId());
            twitter.likeTweet(user1.getId(), 1); // Assuming tweet ID 1 exists
        });

        Thread thread2 = new Thread(() -> {
            twitter.postTweet(user2.getId(), "Hello from Bob!");
            twitter.follow(user2.getId(), user3.getId());
            twitter.retweet(user2.getId(), 1); // Assuming tweet ID 1 exists
        });

        Thread thread3 = new Thread(() -> {
            twitter.postTweet(user3.getId(), "Hello from Charlie!");
            twitter.follow(user3.getId(), user1.getId());
            twitter.commentOnTweet(user3.getId(), 1, "Nice tweet!"); // Assuming tweet ID 1 exists
        });

        // Start threads
        thread1.start();
        thread2.start();
        thread3.start();

        // Join threads to ensure they complete before the main thread ends
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print user information and feed to verify actions
        System.out.println("User1: " + user1.getName() + ", Followers: " + user1.getFollowers().size());
        System.out.println("User2: " + user2.getName() + ", Followers: " + user2.getFollowers().size());
        System.out.println("User3: " + user3.getName() + ", Followers: " + user3.getFollowers().size());

        List<Tweet> feed = twitter.viewFeed(user1.getId());
        System.out.println("User1's Feed:");
        for (Tweet tweet : feed) {
            System.out.println(tweet.getContent());
        }
    }
}