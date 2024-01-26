package fr.an.test.sparkserver.appdata.specific;

import fr.an.exprlib.dto.specific.UserDTO;
import fr.an.test.sparkserver.appdata.AppDatasets;
import fr.an.test.sparkserver.appdata.AppDbMetadata;
import fr.an.test.sparkserver.sql.TableGenericQueryService;
import org.springframework.stereotype.Service;

@Service
public class UserQueryService extends TableGenericQueryService<UserDTO> {
    public UserQueryService(AppDatasets appDatasets) {
        super(AppDbMetadata.User_, appDatasets.userDs());
    }

}
