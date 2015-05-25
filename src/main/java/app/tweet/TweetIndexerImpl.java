package app.tweet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import app.party.Party;
import app.politician.Politician;

@Service
public class TweetIndexerImpl implements TweetIndexer {

    private static List<String> IGNORED_WORDS = Arrays.asList("", "o", "får", "the", "finns", "säger", "to", "få",
            "nya", "se", "dag", "dagens", "ser", "ta", "en", "just", "går", "lite", "också", "väg", "läs", "skriver",
            "alla", "allt", "att", "att ", "av", "blev", "bli", "bli ", "blir", "blivit", "de", "dem", "dem ", "den",
            "den ", "denna", "denna ", "deras", "deras ", "dess", "dessa", "dessa ", "det", "det ", "detta", "detta ",
            "dig", "dig ", "din", "din ", "dina", "ditt", "du", "där", "där ", "då", "efter", "efter ", "ej", "eller",
            "eller ", "en", "er", "era", "era ", "ert", "ert ", "ett", "ett ", "från", "för", "för ", "ha", "hade",
            "han", "han ", "hans", "har", "har ", "henne", "henne ", "hennes", "hon", "hon ", "honom", "honom ", "hur",
            "hur ", "här", "här ", "i", "i ", "icke", "ingen", "ingen ", "inom", "inte", "jag", "jag ", "ju", "kan",
            "kan ", "kunde", "kunde ", "man", "man ", "med", "med ", "mellan", "men", "men ", "mig", "mig ", "min",
            "min ", "mina", "mitt", "mot", "mot ", "mycket", "ni", "nu", "när", "när ", "någon", "någon ", "något",
            "något ", "några", "några ", "och", "och ", "om", "oss", "oss ", "på", "samma", "samma ", "sedan",
            "sedan ", "sig", "sig ", "sin", "sin ", "sina", "sitta", "sitta ", "själv", "själv ", "skulle", "som",
            "som ", "så", "sådan", "sådan ", "sådana", "sådant", "till", "under", "under ", "upp", "upp ", "ut",
            "utan", "vad", "vad ", "var", "var ", "vara", "varför", "varit", "varit ", "varje", "varje ", "vars",
            "vart", "vem", "vem ", "vi", "vid", "vid ", "vilka", "vilka ", "vilkas", "vilken", "vilket", "vår", "vår ",
            "våra", "vårt", "än", "är", "åt", "över", "@", "#svpol");

    @Override
    public TweetDocument createTweetDocument(Tweet tweet) {
        TweetDocument tweetDocument = new TweetDocument();
        tweetDocument.setId(tweet.getId());
        tweetDocument.setPostedAt(tweet.getPostedAt());
        tweetDocument.setText(tweet.getText());
        tweetDocument.setTweetedWords(splitWords(tweet.getText()));

        Politician politician = tweet.getPolitician();
        tweetDocument.setPoliticianId(politician.getId());
        tweetDocument.setPoliticianName(politician.getName());
        tweetDocument.setPoliticianTwitterScreenName(politician.getTwitterScreenName());

        Party party = politician.getParty();
        tweetDocument.setPartyName(party.getName());
        tweetDocument.setPartyUrlName(party.getUrlName());

        return tweetDocument;
    }

    private List<String> splitWords(String text) {
        String string = text.toLowerCase();
        string = string.replaceAll("[^a-zåäö\\s#@]+", "").replaceAll("\\s+", " ");
        List<String> list = new ArrayList<String>(Arrays.asList(string.split(" ")));
        list.removeAll(IGNORED_WORDS);
        return list;
    }
}
