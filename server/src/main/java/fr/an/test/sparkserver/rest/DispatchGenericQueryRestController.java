package fr.an.test.sparkserver.rest;

import fr.an.exprlib.dto.QuerySimpleTableColumnsParamsDTO;
import fr.an.exprlib.dto.metadata.RowDTO;
import fr.an.exprlib.dto.metadata.TableInfoDTO;
import fr.an.test.sparkserver.appdata.AppDispatchGenericQueryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/dispatch-table/{tableName}",
        produces = "application/json")
public class DispatchGenericQueryRestController extends AbstractRestController {

    private final AppDispatchGenericQueryService delegate;

    //---------------------------------------------------------------------------------------------

    protected DispatchGenericQueryRestController(AppDispatchGenericQueryService delegate) {
        super("/api/dispatch-table/${tableName}");
        this.delegate = delegate;
    }

    //---------------------------------------------------------------------------------------------

    @GetMapping("/tableInfo")
    @Operation(summary = "get table info metadata")
    public TableInfoDTO tableInfo(
            @PathVariable("tableName") String tableName) {
        return delegate.dispatchTableInfo(tableName);
    }

    @GetMapping("")
    @Operation(summary = "list all")
    public List<Object> list(
            @PathVariable("tableName") String tableName) {
        return withLog("GET", "", "",
                () -> delegate.dispatchFindAllDtos(tableName));
    }

    @GetMapping("/first")
    @Operation(summary = "list first N(default=20)")
    public List<Object> first(
            @PathVariable("tableName") String tableName,
            @RequestParam(name="limit", defaultValue = "20") int limit) {
        return withLog("GET", "/first", "limit:" + limit,
                () -> delegate.dispatchFindFirstDtos(tableName, limit));
    }

    @PutMapping("/query-simple-cols")
    @Operation(summary = "query simple columns")
    public List<RowDTO> query(
            @PathVariable("tableName") String tableName,
            @RequestBody QuerySimpleTableColumnsParamsDTO req) {
        return withLog("PUT", "/query-simple-cols", "tableName:" + tableName + " req:" + req,
                () -> delegate.dispatchQuerySimpleCols(tableName, req));
    }

}
