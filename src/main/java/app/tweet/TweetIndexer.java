package app.tweet;

import app.tweet.domain.Tweet;

public interface TweetIndexer {
    TweetDocument createTweetDocument(Tweet tweet);
}
