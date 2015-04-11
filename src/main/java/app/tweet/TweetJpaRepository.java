package app.tweet;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface TweetJpaRepository extends ElasticsearchCrudRepository<Tweet, Long> {

}
