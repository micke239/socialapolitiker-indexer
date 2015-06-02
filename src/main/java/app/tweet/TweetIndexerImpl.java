package app.tweet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.party.Party;
import app.politician.Politician;
import app.tweet.domain.Tweet;
import app.tweet.domain.TweetUrl;

import com.twitter.Extractor;

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
            "våra", "vårt", "än", "är", "åt", "över", "@", "#svpol", "of", "a", "år", "via", "is", "and", "for", "on");

    @Autowired
    Extractor extractor;

    @Override
    public TweetDocument createTweetDocument(Tweet tweet) {
        TweetDocument tweetDocument = new TweetDocument();
        tweetDocument.setId(tweet.getId());
        tweetDocument.setPostedAt(tweet.getPostedAt());
        handleText(tweet, tweetDocument);

        Politician politician = tweet.getPolitician();
        tweetDocument.setPoliticianId(politician.getId());
        tweetDocument.setPoliticianName(politician.getName());
        tweetDocument.setPoliticianTwitterScreenName(politician.getTwitterScreenName());

        Party party = politician.getParty();
        tweetDocument.setPartyName(party.getName());
        tweetDocument.setPartyUrlName(party.getUrlName());

        return tweetDocument;
    }

    private void handleText(Tweet tweet, TweetDocument tweetDocument) {
        String text = tweet.getText();
        text = StringEscapeUtils.unescapeHtml4(text);

        tweetDocument.setText(tweet.getText());
        text = handleHashtags(tweet, tweetDocument, text);
        text = handleUrls(tweet, tweetDocument, text);
        text = handleUserMentions(tweet, tweetDocument, text);

        tweetDocument.setTweetedWords(splitWords(text));
    }

    private String handleHashtags(Tweet tweet, TweetDocument tweetDocument, String text) {
        List<String> extractedHashtags = extractor.extractHashtags(text);

        Set<String> hashtags = extractedHashtags.stream().map(hashtag -> {
            return "#" + hashtag;
        }).collect(Collectors.toSet());

        text = hashtags.stream().reduce(text, (string, hashtag) -> {
            return string.replace(hashtag, "");
        });

        tweetDocument.setHashtags(hashtags);

        return text;
    }

    private String handleUrls(Tweet tweet, TweetDocument tweetDocument, String text) {
        // first handle logged urls so that we get the actual url
        Set<String> urls = new HashSet<String>();

        for (TweetUrl url : tweet.getUrls()) {
            String urlTextInTweet = tweet.getText().substring(url.getStartingAt(), url.getEndingAt());
            urls.add(url.getUrl());
            text = text.replace(urlTextInTweet, "");
        }

        // then try to parse more urls
        List<String> extractedUrls = extractor.extractURLs(text);
        text = extractedUrls.stream().reduce(text, (string, url) -> {
            return string.replace(url, "");
        });
        urls.addAll(extractedUrls);

        tweetDocument.setUrls(urls);

        return text;
    }

    private String handleUserMentions(Tweet tweet, TweetDocument tweetDocument, String text) {
        List<String> extractedMentions = extractor.extractMentionedScreennames(text);

        Set<String> userMentions = extractedMentions.stream().map(hashtag -> {
            return "@" + hashtag;
        }).collect(Collectors.toSet());

        text = userMentions.stream().reduce(text, (string, userMention) -> {
            return string.replace(userMention, "");
        });

        tweetDocument.setUserMentions(userMentions);

        return text;
    }

    private List<String> splitWords(String text) {
        String string = text.toLowerCase();
        string = string.replaceAll("[^a-zåäö\\s#@]+", "").replaceAll("\\s+", " ");
        List<String> list = new ArrayList<String>(Arrays.asList(string.split(" ")));
        list.removeAll(IGNORED_WORDS);
        return list;
    }
}
