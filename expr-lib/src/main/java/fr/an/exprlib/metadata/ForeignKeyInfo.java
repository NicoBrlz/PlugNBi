package fr.an.exprlib.metadata;

import com.google.common.collect.ImmutableList;
import fr.an.exprlib.dto.metadata.ForeignKeyInfoDTO;
import lombok.Value;

@Value
public class ForeignKeyInfo {

    public final JoinRelationship joinRelationship;

    public String leftFKColName() { return joinRelationship.columns().get(0).leftColumn(); }
    public String rightTableName() { return joinRelationship.rightTable(); }

    public ForeignKeyInfo(String leftTableName, String leftFKColName,
                          String rightTableName,
                          String rightPKColName // redundant with TableInfo of right table
    ) {
        this.joinRelationship = new JoinRelationship(leftTableName, rightTableName,
                ImmutableList.of(new JoinedColumn(leftFKColName, rightPKColName)));
    }

    public ForeignKeyInfoDTO toDTO() {
        return new ForeignKeyInfoDTO(joinRelationship);
    }

}
