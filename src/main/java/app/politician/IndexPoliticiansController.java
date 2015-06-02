package app.politician;

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

@RestController
public class IndexPoliticiansController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    PoliticianJpaRepository politicianJpaRepository;

    @Autowired
    PoliticianIndexer politicianIndexer;

    @Autowired
    EntityManager entityManager;

    @RequestMapping("/index-politicians")
    public String indexPoliticians(
            @RequestParam(value = "clear", defaultValue = "false", required = false) boolean clearIndex) {
        if (clearIndex) {
            clearIndex();
        }

        Pageable pageable = new PageRequest(0, 1000);
        while (pageable != null) {
            Page<Politician> politicians = politicianJpaRepository.findActivePoliticians(pageable);

            List<IndexQuery> indexQueries = politicians
                    .getContent()
                    .stream()
                    .map((politician) -> {
                        try {
                            PoliticianDocument politicianDocument = politicianIndexer
                                    .createPoliticianDocument(politician);
                            return new IndexQueryBuilder().withObject(politicianDocument)
                                    .withId(politicianDocument.getId().toString()).build();
                        } catch (Exception e) {
                            log.error("Uncaught exception caught while indexing tweet " + politician.getId(), e);
                        }

                        return null;
                    }).collect(Collectors.toList());

            elasticsearchOperations.bulkIndex(indexQueries);

            log.info("Done page " + politicians.getNumber() + " of " + politicians.getTotalPages());

            pageable = politicians.nextPageable();

            // necessary for some reason :( i give up
            entityManager.clear();
        }

        return "DONE!";
    }

    private void clearIndex() {
        if (elasticsearchOperations.indexExists(PoliticianDocument.class)) {
            elasticsearchOperations.deleteIndex(PoliticianDocument.class);
        }
    }
}
