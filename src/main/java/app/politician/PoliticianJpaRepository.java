package app.politician;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.tweet.domain.Tweet;

public interface PoliticianJpaRepository extends JpaRepository<Tweet, Long> {

    @Query("select p from Politician p where p.forceDisabledAt is null and p.tweets is not empty")
    Page<Politician> findActivePoliticians(Pageable pageable);

}
