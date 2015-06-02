package app.tweet.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tweet_hashtag")
public class TweetHashtag {
    private Long id;
    private String tag;
    private Short startingAt;
    private Short endingAt;
    private Tweet tweet;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Short getStartingAt() {
        return startingAt;
    }

    public void setStartingAt(Short startingAt) {
        this.startingAt = startingAt;
    }

    public Short getEndingAt() {
        return endingAt;
    }

    public void setEndingAt(Short endingAt) {
        this.endingAt = endingAt;
    }

    @ManyToOne
    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

}
