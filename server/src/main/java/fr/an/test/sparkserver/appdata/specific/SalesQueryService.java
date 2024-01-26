package fr.an.test.sparkserver.appdata.specific;

import fr.an.exprlib.dto.specific.SalesDTO;
import fr.an.test.sparkserver.appdata.AppDatasets;
import fr.an.test.sparkserver.appdata.AppDbMetadata;
import fr.an.test.sparkserver.sql.TableGenericQueryService;
import org.springframework.stereotype.Service;

@Service
public class SalesQueryService extends TableGenericQueryService<SalesDTO> {

    public SalesQueryService(AppDatasets appDatasets) {
        super(AppDbMetadata.Sales_, appDatasets.salesDs());
    }

}
