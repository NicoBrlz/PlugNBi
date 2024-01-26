package fr.an.test.sparkserver.rest;

import fr.an.exprlib.dto.expr.ParseQueryRequestDTO;
import fr.an.exprlib.dto.expr.ParseQueryResponseDTO;
import fr.an.test.sparkserver.sql.analysis.SqlExprParserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/query",
        produces = "application/json")
public class QueryRestController extends AbstractRestController {

    protected final SqlExprParserService sqlExprService;

    //---------------------------------------------------------------------------------------------
    public QueryRestController(SqlExprParserService sqlExprService) {
        super("/api/query");
        this.sqlExprService = sqlExprService;
    }

    //---------------------------------------------------------------------------------------------

    @PostMapping(path="/test-parse-query")
    public ParseQueryResponseDTO parseQueryRequest(@RequestBody ParseQueryRequestDTO req) {
        return sqlExprService.parseQueryRequest(req);
    }

}
