package fr.an.test.sparkserver.rest.specific;

import fr.an.exprlib.dto.specific.VenueDTO;
import fr.an.test.sparkserver.appdata.specific.VenueQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/specific/venue")
@Tag(name="Venue Rest", // do not modify, name used in generated code
    description="rest api for 'Venue' table (dimension table)")
public class VenueQueryRestController extends AbstractQueryRestController<VenueDTO> {

    //---------------------------------------------------------------------------------------------

    public VenueQueryRestController(VenueQueryService queryService) {
        super(queryService);
    }

    //---------------------------------------------------------------------------------------------

}
