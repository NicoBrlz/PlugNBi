package fr.an.exprlib.sql.datasetExpr;

import fr.an.exprlib.sql.expr.SimpleExpr;
import fr.an.exprlib.sql.expr.SimpleExpr.NamedSimpleExpr;
import fr.an.exprlib.sql.expr.SimpleExpr.SimpleExprSortOrder;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * cf spark org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
 */
public abstract class DatasetExpr {

    public abstract void accept(DatasetExprVisitor visitor);

    public abstract <TRes, TParam> TRes accept2(DatasetExprVisitor2<TRes, TParam> visitor, TParam param);

    //---------------------------------------------------------------------------------------------

    @AllArgsConstructor
    public static class TableScanDatasetExpr extends DatasetExpr {
        public String dbName;
        public String tableName;

        @Override
        public void accept(DatasetExprVisitor visitor) {
            visitor.caseTableScan(this);
        }

        @Override
        public <TRes, TParam> TRes accept2(DatasetExprVisitor2<TRes, TParam> visitor, TParam param) {
            return visitor.caseTableScan(this, param);
        }

    }

    //---------------------------------------------------------------------------------------------

    @AllArgsConstructor
    public static class ProjectDatasetExpr extends DatasetExpr {
        public DatasetExpr child;
        public List<NamedSimpleExpr> projectList;

        @Override
        public void accept(DatasetExprVisitor visitor) {
            visitor.caseProject(this);
        }

        @Override
        public <TRes, TParam> TRes accept2(DatasetExprVisitor2<TRes, TParam> visitor, TParam param) {
            return visitor.caseProject(this, param);
        }
    }

    //---------------------------------------------------------------------------------------------

    /**
     * cf org.apache.spark.sql.catalyst.plans.logical.Filter
     */
    @AllArgsConstructor
    public static class FilterDatasetExpr extends DatasetExpr {
        public DatasetExpr child;
        public SimpleExpr condition;

        @Override
        public void accept(DatasetExprVisitor visitor) {
            visitor.caseFilter(this);
        }

        @Override
        public <TRes, TParam> TRes accept2(DatasetExprVisitor2<TRes, TParam> visitor, TParam param) {
            return visitor.caseFilter(this, param);
        }

    }

    //---------------------------------------------------------------------------------------------

    /**
     * cf
     */
    @AllArgsConstructor
    public static class LimitDatasetExpr extends DatasetExpr {
        public DatasetExpr child;
        public long limit;

        @Override
        public void accept(DatasetExprVisitor visitor) {
            visitor.caseLimit(this);
        }

        @Override
        public <TRes, TParam> TRes accept2(DatasetExprVisitor2<TRes, TParam> visitor, TParam param) {
            return visitor.caseLimit(this, param);
        }

    }

    //---------------------------------------------------------------------------------------------

    /**
     * Distinct
     */
    @AllArgsConstructor
    public static class DistinctDataSetExpr extends DatasetExpr {
        public DatasetExpr underlying;

        @Override
        public void accept(DatasetExprVisitor visitor) {
            visitor.caseDistinct(this);
        }

        @Override
        public <TRes, TParam> TRes accept2(DatasetExprVisitor2<TRes, TParam> visitor, TParam param) {
            return visitor.caseDistinct(this, param);
        }
    }


    //---------------------------------------------------------------------------------------------

    /**
     * "UNION ALL"
     */
    @AllArgsConstructor
    public static class UnionDatasetExpr extends DatasetExpr {
        public List<DatasetExpr> children;
        public boolean byName;
        public boolean allowMissingCol;

        @Override
        public void accept(DatasetExprVisitor visitor) {
            visitor.caseUnion(this);
        }

        @Override
        public <TRes, TParam> TRes accept2(DatasetExprVisitor2<TRes, TParam> visitor, TParam param) {
            return visitor.caseUnion(this, param);
        }
    }

    //---------------------------------------------------------------------------------------------

    // TOADD
//    public static class ExceptDataSetExpr extends DatasetExpr {
//
//    }
//    public static class IntersectDataSetExpr extends DatasetExpr {
//
//    }

    //---------------------------------------------------------------------------------------------

    /**
     * Join
     */
    @AllArgsConstructor
    public static class JoinDataSetExpr extends DatasetExpr {
        public DatasetExpr left;
        public DatasetExpr right;
        public EnumJoinType joinType;
        public SimpleExpr joinExpression;
        public JoinHint hint;

        @Override
        public void accept(DatasetExprVisitor visitor) {
            visitor.caseJoin(this);
        }

        @Override
        public <TRes, TParam> TRes accept2(DatasetExprVisitor2<TRes, TParam> visitor, TParam param) {
            return visitor.caseJoin(this, param);
        }
    }

    public enum EnumJoinType {
        INNER, CROSS, LEFT_OUTER, RIGHT_OUTER, FULL_OUTER, LEFT_SEMI, LEFT_ANTI
    }

    public record JoinHint(EnumHintInfo leftHint, EnumHintInfo rightHint) {
    }

    /**
     * cf org.apache.spark.sql.catalyst.plans.logical.JoinStrategyHint
     */
    public enum EnumHintInfo {
        /**
         * The hint for broadcast hash join or broadcast nested loop join, depending on the availability of
         * equi-join keys.
         */
        BROADCAST, // "BROADCAST", "BROADCASTJOIN", "MAPJOIN"

        SORT_MERGE, // "SHUFFLE_MERGE", "MERGE", "MERGEJOIN"

        SHUFFLE_HASH,
        /**
         * The hint for shuffle-and-replicate nested loop join, a.k.a. cartesian product join.
         */
        SHUFFLE_REPLICATE_NL,

        /**
         * An internal hint to discourage broadcast hash join, used by adaptive query execution.
         */
        NO_BROADCAST_HASH,

        /**
         * An internal hint to encourage shuffle hash join, used by adaptive query execution.
         */
        PREFER_SHUFFLE_HASH,

        /**
         * An internal hint to prohibit broadcasting and replicating one side of a join. This hint is used
         * by some rules where broadcasting or replicating a particular side of the join is not permitted,
         * such as the cardinality check in MERGE operations.
         */
        NO_BROADCAST_AND_REPLICATION
    }

    //---------------------------------------------------------------------------------------------

    @AllArgsConstructor
    public static class LateralJoinDatasetExpr extends DatasetExpr {
        public DatasetExpr left;
        public LateralSubqueryExpr right;
        public EnumJoinType joinType;
        public SimpleExpr condition;

        @Override
        public void accept(DatasetExprVisitor visitor) {
            visitor.caseLateralJoin(this);
        }

        @Override
        public <TRes, TParam> TRes accept2(DatasetExprVisitor2<TRes, TParam> visitor, TParam param) {
            return visitor.caseLateralJoin(this, param);
        }
    }

    @AllArgsConstructor
    public static class LateralSubqueryExpr {
        // DatasetExpr plan;
        public List<SimpleExpr> outerAttrs;
        // List<SimpleExpr> joinCond;
        // EnumHintInfo hint;
    }

    //---------------------------------------------------------------------------------------------

    /**
     * Aggregate ... for "GROUP BY"
     */
    @AllArgsConstructor
    public static class AggregateDatasetExpr extends DatasetExpr {
        public List<SimpleExpr> groupingExpressions;
        public List<NamedSimpleExpr> aggregateExpressions;
        public DatasetExpr child;

        @Override
        public void accept(DatasetExprVisitor visitor) {
            visitor.caseAggregate(this);
        }

        @Override
        public <TRes, TParam> TRes accept2(DatasetExprVisitor2<TRes, TParam> visitor, TParam param) {
            return visitor.caseAggregate(this, param);
        }
    }

    //---------------------------------------------------------------------------------------------

    public static class WindowDatasetExpr extends DatasetExpr {
        public List<NamedSimpleExpr> windowExpressions;
        public List<SimpleExpr> partitionSpec;
        public List<SimpleExprSortOrder> orderSpec;
        public DatasetExpr child;

        @Override
        public void accept(DatasetExprVisitor visitor) {
            visitor.caseWindow(this);
        }

        @Override
        public <TRes, TParam> TRes accept2(DatasetExprVisitor2<TRes, TParam> visitor, TParam param) {
            return visitor.caseWindow(this, param);
        }
    }


    //---------------------------------------------------------------------------------------------

    //TOADD
//    /**
//     * cf org.apache.spark.sql.catalyst.plans.logical.Expand
//     * Apply a number of projections to every input row, hence we will get multiple output rows for
//     * an input row.
//     */
//    public static class ExpandDataSetExpr extends DatasetExpr {
//        public DatasetExpr child;
//        public List<List<SimpleExpr>> projections;
//        public List<String> output;
//    }

//TOADD
//    public static class RangeDatasetExpr extends DatasetExpr {
//            long start, end, step;
//            Integer numSlices;
//  }

}
