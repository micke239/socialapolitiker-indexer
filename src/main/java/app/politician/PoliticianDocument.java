package app.politician;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "politician")
public class PoliticianDocument {
    private @Id Long id;
    private String name;
    private String partyName;
    private String partyUrlName;
    private String twitterScreenName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyUrlName() {
        return partyUrlName;
    }

    public void setPartyUrlName(String partyUrlName) {
        this.partyUrlName = partyUrlName;
    }

    public void setTwitterScreenName(String twitterScreenName) {
        this.twitterScreenName = twitterScreenName;
    }

    public String getTwitterScreenName() {
        return twitterScreenName;
    }

}
