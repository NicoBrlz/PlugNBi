package fr.an.test.sparkserver.sql.analysis;

import fr.an.exprlib.dto.QueryRequestDTO;
import fr.an.exprlib.dto.expr.ParseQueryRequestDTO;
import fr.an.exprlib.dto.expr.ParseQueryResponseDTO;
import lombok.val;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalyst.expressions.Expression;
import org.apache.spark.sql.catalyst.parser.ParseException;
import org.apache.spark.sql.catalyst.parser.ParserInterface;
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan;
import org.apache.spark.sql.types.StructType;
import org.springframework.stereotype.Service;

@Service
public class SqlExprParserService {

    private final SparkSession sparkSession;
    private final ParserInterface sqlParser;

    public SqlExprParserService(SparkSession sparkSession) {
        this.sparkSession = sparkSession;
        this.sqlParser = sparkSession.sessionState().sqlParser();
    }

    public Expression parseExpression(String text) {
        try {
            return sqlParser.parseExpression(text);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse expression text '" + text + "'", e);
        }
    }
    public LogicalPlan parseQuery(String text) {
        try {
            return sqlParser.parseQuery(text);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse query text '" + text + "'", e);
        }
    }

    public String toSql(Expression expr) {
        return expr.sql();
    }

    public ParseQueryResponseDTO parseQueryRequest(ParseQueryRequestDTO req) {
        // LogicalPlan parsedPlan = parseQuery(req.text);
        // expr.resolved() => false !
        // sparkSession.sessionState().analyzer().resolver() ???

        val ds = sparkSession.sql(req.text);
        StructType dsSchema = ds.schema();

        LogicalPlan analyzed = ds.queryExecution().analyzed();

        String parsedToJson = analyzed.toJSON();
        // not resolved yet.. String parsedToSchema = parsedPlan.printSchema();

        // TODO
        QueryRequestDTO queryDto = null;

        return new ParseQueryResponseDTO(parsedToJson, queryDto);
    }

}
