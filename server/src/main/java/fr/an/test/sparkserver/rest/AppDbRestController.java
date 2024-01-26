package fr.an.test.sparkserver.rest;

import fr.an.exprlib.dto.expr.ParseQueryRequestDTO;
import fr.an.exprlib.dto.expr.ParseQueryResponseDTO;
import fr.an.exprlib.dto.metadata.DatabaseInfoDTO;
import fr.an.test.sparkserver.appdata.AppDbMetadata;
import fr.an.test.sparkserver.sql.analysis.SqlExprParserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/db",
        produces = "application/json")
public class AppDbRestController extends AbstractRestController {

    //---------------------------------------------------------------------------------------------

    public AppDbRestController() {
        super("/api/db");
    }

    //---------------------------------------------------------------------------------------------

    @GetMapping(path="/info")
    public DatabaseInfoDTO getDatabaseInfo() {
        return AppDbMetadata.DB.toDTO();
    }

}
