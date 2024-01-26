package fr.an.test.sparkserver.appdata.specific;

import fr.an.exprlib.dto.specific.EventDTO;
import fr.an.test.sparkserver.appdata.AppDatasets;
import fr.an.test.sparkserver.appdata.AppDbMetadata;
import fr.an.test.sparkserver.sql.TableGenericQueryService;
import org.springframework.stereotype.Service;

@Service
public class EventQueryService extends TableGenericQueryService<EventDTO> {
    public EventQueryService(AppDatasets appDatasets) {
        super(AppDbMetadata.Event_, appDatasets.eventDs());
    }

}
