package fr.an.test.sparkserver.appdata.specific;

import fr.an.exprlib.dto.specific.DateDTO;
import fr.an.test.sparkserver.appdata.AppDatasets;
import fr.an.test.sparkserver.appdata.AppDbMetadata;
import fr.an.test.sparkserver.sql.TableGenericQueryService;
import org.springframework.stereotype.Service;

@Service
public class DateQueryService extends TableGenericQueryService<DateDTO> {
    public DateQueryService(AppDatasets appDatasets) {
        super(AppDbMetadata.Date_, appDatasets.dateDs());
    }

}
