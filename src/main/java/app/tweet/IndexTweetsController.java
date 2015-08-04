package app.tweet;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.tweet.domain.Tweet;

@RestController
public class IndexTweetsController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    TweetJpaRepository tweetJpaRepository;

    @Autowired
    TweetIndexer tweetIndexer;

    @Autowired
    EntityManager entityManager;

    @RequestMapping("/index-tweets")
    public String indexTweets(
            @RequestParam(value = "clear", defaultValue = "false", required = false) boolean clearIndex) {
        if (clearIndex) {
            clearIndex();
        }

        Pageable pageable = new PageRequest(0, 1000);
        while (pageable != null) {
            Page<Tweet> tweets = tweetJpaRepository.findTweetsFromActivePoliticians(pageable);

            List<IndexQuery> indexQueries = tweets
                    .getContent()
                    .stream()
                    .map((tweet) -> {
                        try {
                            TweetDocument tweetDocument = tweetIndexer.createTweetDocument(tweet);
                            return new IndexQueryBuilder().withObject(tweetDocument)
                                    .withId(tweetDocument.getId().toString()).build();
                        } catch (Exception e) {
                            log.error("Uncaught exception caught while indexing tweet " + tweet.getId(), e);
                        }

                        return null;
                    }).collect(Collectors.toList());

            elasticsearchOperations.bulkIndex(indexQueries);

            log.info("Done page " + tweets.getNumber() + " of " + tweets.getTotalPages());

            pageable = tweets.nextPageable();

            // necessary for some reason :( i give up
            entityManager.clear();
        }

        return "DONE!";
    }

    private void clearIndex() {
        if (elasticsearchOperations.indexExists(TweetDocument.class)) {
            elasticsearchOperations.deleteIndex(TweetDocument.class);
            elasticsearchOperations.createIndex(TweetDocument.class);
            elasticsearchOperations.putMapping(TweetDocument.class);
            elasticsearchOperations.refresh(TweetDocument.class, true);
        }
    }

}
