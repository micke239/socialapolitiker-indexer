package app.tweet.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tweet_user_mention")
public class TweetUserMention {
    private Long id;
    private String screenName;
    private String name;
    private Tweet tweet;
    private Short startingAt;
    private Short endingAt;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
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

}
