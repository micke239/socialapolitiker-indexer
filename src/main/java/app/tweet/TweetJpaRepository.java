package app.tweet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.tweet.domain.Tweet;

public interface TweetJpaRepository extends JpaRepository<Tweet, Long> {

    @Query("select t from Tweet t join t.politician p where p.forceDisabledAt is null")
    Page<Tweet> findTweetsFromActivePoliticians(Pageable pageable);

}
