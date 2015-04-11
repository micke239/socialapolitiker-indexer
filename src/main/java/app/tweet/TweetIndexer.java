package app.tweet;

public interface TweetIndexer {
    TweetDocument createTweetDocument(Tweet tweet);
}
