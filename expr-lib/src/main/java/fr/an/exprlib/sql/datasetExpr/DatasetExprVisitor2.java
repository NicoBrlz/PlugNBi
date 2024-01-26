package fr.an.exprlib.sql.datasetExpr;

import fr.an.exprlib.sql.datasetExpr.DatasetExpr.*;

public abstract class DatasetExprVisitor2<TRes, TParam> {

    public abstract TRes caseTableScan(TableScanDatasetExpr p, TParam param);

    public abstract TRes caseProject(ProjectDatasetExpr p, TParam param);

    public abstract TRes caseFilter(FilterDatasetExpr p, TParam param);

    public abstract TRes caseLimit(LimitDatasetExpr p, TParam param);

    public abstract TRes caseDistinct(DistinctDataSetExpr p, TParam param);

    public abstract TRes caseWindow(WindowDatasetExpr p, TParam param);

    public abstract TRes caseJoin(JoinDataSetExpr p, TParam param);

    public abstract TRes caseLateralJoin(LateralJoinDatasetExpr p, TParam param);

    public abstract TRes caseAggregate(AggregateDatasetExpr p, TParam param);

    public abstract TRes caseUnion(UnionDatasetExpr p, TParam param);


}
