package fr.an.test.sparkserver.appdata.specific;

import fr.an.exprlib.dto.specific.CategoryDTO;
import fr.an.test.sparkserver.appdata.AppDatasets;
import fr.an.test.sparkserver.appdata.AppDbMetadata;
import fr.an.test.sparkserver.sql.TableGenericQueryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryQueryService extends TableGenericQueryService<CategoryDTO> {
    public CategoryQueryService(AppDatasets appDatasets) {
        super(AppDbMetadata.Category_, appDatasets.categoryDs());
    }

}
