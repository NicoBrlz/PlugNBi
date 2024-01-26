package fr.an.test.sparkserver.appdata.specific;

import fr.an.exprlib.dto.specific.ListingDTO;
import fr.an.test.sparkserver.appdata.AppDatasets;
import fr.an.test.sparkserver.appdata.AppDbMetadata;
import fr.an.test.sparkserver.sql.TableGenericQueryService;
import org.springframework.stereotype.Service;

@Service
public class ListingQueryService extends TableGenericQueryService<ListingDTO> {
    public ListingQueryService(AppDatasets appDatasets) {
        super(AppDbMetadata.Listing_, appDatasets.listingDs());
    }

}
