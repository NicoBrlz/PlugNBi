package fr.an.exprlib.dto;

import fr.an.exprlib.dto.expr.AliasedExprTextDTO;
import fr.an.exprlib.dto.expr.ExprDTO;
import fr.an.exprlib.dto.expr.JoinDTO;
import fr.an.exprlib.dto.expr.OrderByDTO;

import java.util.List;

public class QueryRequestDTO {

    public List<AliasedExprTextDTO> select;

    public String from;

    public List<JoinDTO> joins;

    public List<ExprDTO> whereConditions;

    public List<OrderByDTO> orderBy;

    /** column references of the select: either the column name, or alias name when defined */
    public List<String> groupBy;

    // TODO should also support "GROUPING SET", "ROLLUP", and "CUBE" !!
    public List<List<String>> groupByGroupingSets;

    /**
     * <PRE>
     *   ROLLUP ( e1, e2, e3, ... )
     * </PRE>
     * is equivalent to N grouping sets
     * <PRE>
     *   GROUPING SETS (
     *     ( e1, e2, e3, ... ),
     *     ...
     *     ( e1, e2 ),
     *     ( e1 ),
     *     ( )
     * )
     * </PRE>
     */
    public List<String> groupByRollup;

    /**
     * <PRE>
     *   CUBE ( a, b, c )
     * </PRE>
     * is equivalent to 2^N grouping sets
     * <PRE>
     *   GROUPING SETS (
     *     ( a, b, c ),
     *     ( a, b    ),
     *     ( a,    c ),
     *     ( a       ),
     *     (    b, c ),
     *     (    b    ),
     *     (       c ),
     *     (         )
     *   )
     * </PRE>
     */
    public List<String> groupByCube;

    public List<ExprDTO> havingConditions;

    public int limit;

}
