package fr.an.test.sparkserver.sql.analysis;

import fr.an.exprlib.dto.expr.ParseQueryRequestDTO;
import fr.an.exprlib.dto.expr.ParseQueryResponseDTO;
import fr.an.test.sparkserver.BaseAppTestCase;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan;
import org.apache.spark.sql.execution.QueryExecution;
import org.apache.spark.sql.execution.SparkPlan;
import org.apache.spark.sql.types.StructType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class SqlExprParserServiceTest extends BaseAppTestCase {

    @Autowired
    protected SparkSession sparkSession;

    @Autowired
    protected SqlExprParserService sut;

    @Test
    public void testParseSql_select_where_id_eq_1() {
        String sql = "SELECT venueid FROM venue v WHERE venueid = 1";

        LogicalPlan logicalPlan = sut.parseQuery(sql);
        Assert.assertNotNull(logicalPlan);
        log.info("sql unresolved logicalPlan:" + logicalPlan);
        // 'Project ['venueid]
        // +- 'Filter ('venueid = 1)
        //    +- 'SubqueryAlias v
        //       +- 'UnresolvedRelation [venue], [], false

        val ds = sparkSession.sql(sql);

        StructType dsSchema = ds.schema();
        log.info("ds.schema:\n" + dsSchema.toString());
        // StructType(StructField(venueid,ShortType,true))

        QueryExecution queryExecution = ds.queryExecution();
        log.info("ds.queryExecution:\n" + queryExecution.toString());
        // == Parsed Logical Plan ==
        // 'Project ['venueid]
        // +- 'Filter ('venueid = 1)
        //    +- 'SubqueryAlias v
        //       +- 'UnresolvedRelation [venue], [], false
        //
        // == Analyzed Logical Plan ==
        // venueid: smallint
        // Project [venueid#1244]
        // +- Filter (cast(venueid#1244 as int) = 1)
        //    +- SubqueryAlias v
        //       +- SubqueryAlias venue
        //          +- View (`venue`, [venueid#1244,venuename#1245,venuecity#1246,venuestate#1247,venueseats#1248])
        //             +- Relation [venueid#1244,venuename#1245,venuecity#1246,venuestate#1247,venueseats#1248] csv
        //
        // == Optimized Logical Plan ==
        // Project [venueid#1244]
        // +- Filter (isnotnull(venueid#1244) AND (venueid#1244 = 1))
        //    +- InMemoryRelation [venueid#1244, venuename#1245, venuecity#1246, venuestate#1247, venueseats#1248], StorageLevel(disk, memory, deserialized, 1 replicas)
        //          +- FileScan csv [venueid#1244,venuename#1245,venuecity#1246,venuestate#1247,venueseats#1248] Batched: false, DataFilters: [], Format: CSV, Location: InMemoryFileIndex(1 paths)[file:/C:/arn/devPerso/test-spark-rest-server/server/src/main/db-csv/ve..., PartitionFilters: [], PushedFilters: [], ReadSchema: struct<venueid:smallint,venuename:string,venuecity:string,venuestate:string,venueseats:int>
        //
        // == Physical Plan ==
        // AdaptiveSparkPlan isFinalPlan=false
        // +- Filter (isnotnull(venueid#1244) AND (venueid#1244 = 1))
        //    +- InMemoryTableScan [venueid#1244], [isnotnull(venueid#1244), (venueid#1244 = 1)]
        //          +- InMemoryRelation [venueid#1244, venuename#1245, venuecity#1246, venuestate#1247, venueseats#1248], StorageLevel(disk, memory, deserialized, 1 replicas)
        //                +- FileScan csv [venueid#1244,venuename#1245,venuecity#1246,venuestate#1247,venueseats#1248] Batched: false, DataFilters: [], Format: CSV, Location: InMemoryFileIndex(1 paths)[file:/C:/arn/devPerso/test-spark-rest-server/server/src/main/db-csv/ve..., PartitionFilters: [], PushedFilters: [], ReadSchema: struct<venueid:smallint,venuename:string,venuecity:string,venuestate:string,venueseats:int>


        LogicalPlan analyzed = queryExecution.analyzed();
        log.info("ds.queryExecution.analyzed():\n" + analyzed.toString());
        // Project [venueid#1244]
        //+- Filter (cast(venueid#1244 as int) = 1)
        //   +- SubqueryAlias v
        //      +- SubqueryAlias venue
        //         +- View (`venue`, [venueid#1244,venuename#1245,venuecity#1246,venuestate#1247,venueseats#1248])
        //            +- Relation [venueid#1244,venuename#1245,venuecity#1246,venuestate#1247,venueseats#1248] csv

        SparkPlan executedPlan = queryExecution.executedPlan();
        log.info("ds.queryExecution.executedPlan:\n" + executedPlan.toString());
        // AdaptiveSparkPlan isFinalPlan=false
        //+- Filter (isnotnull(venueid#1244) AND (venueid#1244 = 1))
        //   +- InMemoryTableScan [venueid#1244], [isnotnull(venueid#1244), (venueid#1244 = 1)]
        //         +- InMemoryRelation [venueid#1244, venuename#1245, venuecity#1246, venuestate#1247, venueseats#1248], StorageLevel(disk, memory, deserialized, 1 replicas)
        //               +- FileScan csv [venueid#1244,venuename#1245,venuecity#1246,venuestate#1247,venueseats#1248] Batched: false, DataFilters: [], Format: CSV, Location: InMemoryFileIndex(1 paths)[file:/C:/arn/devPerso/test-spark-rest-server/server/src/main/db-csv/ve..., PartitionFilters: [], PushedFilters: [], ReadSchema: struct<venueid:smallint,venuename:string,venuecity:string,venuestate:string,venueseats:int>

        long count = ds.count();
        log.info("test sql.. ds.count:" + count);

        ParseQueryResponseDTO resp = sut.parseQueryRequest(new ParseQueryRequestDTO(sql));
        Assert.assertNotNull(resp);
    }
}
