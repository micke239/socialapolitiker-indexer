package app.tweet;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "tweet")
public class TweetDocument {

    private @Id Long id;
    private String text;
    private @Field(type = FieldType.String, index = FieldIndex.not_analyzed) List<String> tweetedWords;
    private Date postedAt;
    private Long politicianId;
    private String politicianName;
    private String politicianTwitterScreenName;
    private String partyString;
    private String partyUrlName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getTweetedWords() {
        return tweetedWords;
    }

    public void setTweetedWords(List<String> tweetedWords) {
        this.tweetedWords = tweetedWords;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPoliticianId() {
        return politicianId;
    }

    public void setPoliticianId(Long politicianId) {
        this.politicianId = politicianId;
    }

    public String getPoliticianName() {
        return politicianName;
    }

    public void setPoliticianName(String politicianName) {
        this.politicianName = politicianName;
    }

    public String getPartyString() {
        return partyString;
    }

    public void setPartyName(String partyString) {
        this.partyString = partyString;
    }

    public String getPartyUrlName() {
        return partyUrlName;
    }

    public void setPartyUrlName(String partyUrlName) {
        this.partyUrlName = partyUrlName;
    }

    public String getPoliticianTwitterScreenName() {
        return politicianTwitterScreenName;
    }

    public void setPoliticianTwitterScreenName(String politicianTwitterScreenName) {
        this.politicianTwitterScreenName = politicianTwitterScreenName;
    }

    public void setPartyString(String partyString) {
        this.partyString = partyString;
    }

}
