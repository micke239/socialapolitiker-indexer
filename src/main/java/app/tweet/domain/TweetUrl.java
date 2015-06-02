package app.tweet.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tweet_url")
public class TweetUrl {
    private Long id;
    private String url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
