package fr.an.test.sparkserver.rest.specific;

import fr.an.exprlib.dto.specific.ListingDTO;
import fr.an.test.sparkserver.appdata.specific.ListingQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/specific/listing")
@Tag(name="Listing Rest", // do not modify, name used in generated code
    description="rest api for 'Listing' table (fact table)")
public class ListingQueryRestController extends AbstractQueryRestController<ListingDTO> {

    //---------------------------------------------------------------------------------------------

    public ListingQueryRestController(ListingQueryService queryService) {
        super(queryService);
    }

    //---------------------------------------------------------------------------------------------

}
