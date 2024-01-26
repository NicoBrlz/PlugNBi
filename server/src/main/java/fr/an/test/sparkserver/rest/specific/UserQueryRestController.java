package fr.an.test.sparkserver.rest.specific;

import fr.an.exprlib.dto.specific.UserDTO;
import fr.an.test.sparkserver.appdata.specific.UserQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/specific/user")
@Tag(name="User Rest", // do not modify, name used in generated code
    description="rest api for 'User' table (dimension table)")
public class UserQueryRestController extends AbstractQueryRestController<UserDTO> {

    //---------------------------------------------------------------------------------------------

    public UserQueryRestController(UserQueryService queryService) {
        super(queryService);
    }

    //---------------------------------------------------------------------------------------------

}
