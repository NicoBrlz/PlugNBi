package fr.an.test.sparkserver.sql;

import fr.an.exprlib.dto.QuerySimpleTableColumnsParamsDTO;
import fr.an.exprlib.dto.metadata.RowDTO;
import fr.an.exprlib.dto.metadata.TableInfoDTO;
import fr.an.exprlib.metadata.TableInfo;
import fr.an.exprlib.sql.eval.ExprEvalUtils;
import fr.an.exprlib.utils.LsUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;

import java.util.List;
import java.util.function.Function;

@Slf4j
public class TableGenericQueryService<T> {

    protected final TableInfo<T> tableInfo;

    protected final Dataset<Row> tableDataset;

    protected final Encoder<T> sparkEncoder;

    //---------------------------------------------------------------------------------------------

    public TableGenericQueryService(TableInfo<T> tableInfo, Dataset<Row> tableDataset) {
        this.tableInfo = tableInfo;
        this.tableDataset = tableDataset;
        this.sparkEncoder = Encoders.bean(tableInfo.schema.objClass);
    }

    //---------------------------------------------------------------------------------------------

    public TableInfoDTO tableInfo() {
        return tableInfo.toDTO();
    }

    public List<T> findAllDtos() {
        return toDtos(tableDataset);
    }

    public List<T> firstDtos(int limit) {
        return toDtos(tableDataset.limit(limit));
    }

    protected List<T> toDtos(Dataset<Row> ds) {
        return ds.as(sparkEncoder).collectAsList();
    }

    public List<RowDTO> querySimpleCols(QuerySimpleTableColumnsParamsDTO req) {
        return querySimpleCols(req.cols, req.limitCount);
    }

    public List<RowDTO> querySimpleCols(List<String> selectCols, int limitCount) {
        List<Function<T,?>> dtoColGetters = LsUtils.map(selectCols, col -> tableInfo.resolveObjectEvalFunc(col));
        Dataset<Row> ds = tableDataset;
        if (limitCount != 0) {
            ds = ds.limit(limitCount);
        }
        // can not call Dataset.map() .. java.io.NotSerializableException
        val dtos = toDtos(ds);
        return LsUtils.map(dtos, dto -> new RowDTO(ExprEvalUtils.applyObjectFunctions(dto, dtoColGetters)));
    }


}
