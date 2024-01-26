package fr.an.exprlib.sql.datasetExpr;

import fr.an.exprlib.sql.datasetExpr.DatasetExpr.*;

public abstract class DatasetExprVisitor {

    public abstract void caseTableScan(TableScanDatasetExpr p);

    public abstract void caseProject(ProjectDatasetExpr p);

    public abstract void caseFilter(FilterDatasetExpr p);

    public abstract void caseLimit(LimitDatasetExpr p);

    public abstract void caseDistinct(DistinctDataSetExpr p);

    public abstract void caseUnion(UnionDatasetExpr p);

    public abstract void caseJoin(JoinDataSetExpr joinDataSetExpr);

    public abstract void caseLateralJoin(LateralJoinDatasetExpr p);

    public abstract void caseAggregate(AggregateDatasetExpr p);

    public abstract void caseWindow(WindowDatasetExpr p);

}
