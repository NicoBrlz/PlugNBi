package fr.an.test.sparkserver.rest.specific;

import fr.an.exprlib.dto.specific.DateDTO;
import fr.an.test.sparkserver.appdata.specific.DateQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/specific/date")
@Tag(name="Date Rest", // do not modify, name used in generated code
    description="rest api for 'Date' table (dimension table)")
public class DateQueryRestController extends AbstractQueryRestController<DateDTO> {

    //---------------------------------------------------------------------------------------------

    public DateQueryRestController(DateQueryService queryService) {
        super(queryService);
    }

    //---------------------------------------------------------------------------------------------

}
