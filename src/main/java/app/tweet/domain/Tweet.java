package app.tweet.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import app.politician.Politician;

@Entity
@Table(name = "tweet")
public class Tweet {
    private Long id;
    private Politician politician;
    private Date postedAt;
    private Date importedAt;
    private Long twitterId;
    private String text;
    private String language;
    private String source;
    private Long retweet;

    private List<TweetUrl> urls;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    public Politician getPolitician() {
        return politician;
    }

    public void setPolitician(Politician politician) {
        this.politician = politician;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public Date getImportedAt() {
        return importedAt;
    }

    public void setImportedAt(Date importedAt) {
        this.importedAt = importedAt;
    }

    public Long getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(Long twitterId) {
        this.twitterId = twitterId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getRetweet() {
        return retweet;
    }

    public void setRetweet(Long retweet) {
        this.retweet = retweet;
    }

    @OneToMany(mappedBy = "tweet", fetch = FetchType.EAGER)
    public List<TweetUrl> getUrls() {
        return urls;
    }

    public void setUrls(List<TweetUrl> urls) {
        this.urls = urls;
    }

}
