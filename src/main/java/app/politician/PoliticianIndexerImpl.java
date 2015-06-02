package app.politician;

import org.springframework.stereotype.Service;

import app.party.Party;

@Service
public class PoliticianIndexerImpl implements PoliticianIndexer {

    @Override
    public PoliticianDocument createPoliticianDocument(Politician politician) {
        PoliticianDocument politicianDocument = new PoliticianDocument();
        politicianDocument.setId(politician.getId());

        politicianDocument.setName(politician.getName());
        politicianDocument.setTwitterScreenName(politician.getTwitterScreenName());

        Party party = politician.getParty();
        politicianDocument.setPartyName(party.getName());
        politicianDocument.setPartyUrlName(party.getUrlName());

        return politicianDocument;
    }

}
