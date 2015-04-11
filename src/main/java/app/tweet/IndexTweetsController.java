package app.tweet;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexTweetsController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    TweetJpaRepository tweetJpaRepository;

    @Autowired
    TweetIndexer tweetIndexer;

    @RequestMapping("/index-tweets")
    public String indexTweets() {
        clearIndex();

        Pageable pageable = new PageRequest(0, 10000);
        while (pageable != null) {
            Page<Tweet> tweets = tweetJpaRepository.findAll(pageable);

            List<IndexQuery> indexQueries = new ArrayList<>();

            tweets.forEach((tweet) -> {
                TweetDocument tweetDocument = tweetIndexer.createTweetDocument(tweet);
                indexQueries.add(new IndexQueryBuilder().withObject(tweetDocument).build());
            });

            elasticsearchOperations.bulkIndex(indexQueries);

            log.info("Done page " + tweets.getNumber() + " of " + tweets.getTotalPages());

            pageable = tweets.nextPageable();
        }

        return "DONE!";
    }

    private void clearIndex() {
        if (elasticsearchOperations.indexExists(TweetDocument.class)) {
            elasticsearchOperations.deleteIndex(TweetDocument.class);
        }
    }

}
