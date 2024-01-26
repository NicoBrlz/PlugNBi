package fr.an.test.sparkserver.rest.specific;

import fr.an.exprlib.dto.specific.SalesDTO;
import fr.an.test.sparkserver.appdata.specific.SalesQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/specific/sales")
@Tag(name="Sales Rest", // do not modify, name used in generated code
    description="rest api for 'Sales' table (fact table)")
public class SalesQueryRestController extends AbstractQueryRestController<SalesDTO> {

    //---------------------------------------------------------------------------------------------

    public SalesQueryRestController(SalesQueryService salesDbService) {
        super(salesDbService);
    }

    //---------------------------------------------------------------------------------------------

}
