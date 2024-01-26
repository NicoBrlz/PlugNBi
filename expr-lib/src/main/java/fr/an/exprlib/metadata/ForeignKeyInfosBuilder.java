package fr.an.exprlib.metadata;

import java.util.ArrayList;
import java.util.List;

public class ForeignKeyInfosBuilder<T> {

    public final String leftTableName;
    protected List<ForeignKeyInfo> ls = new ArrayList<ForeignKeyInfo>();

    //---------------------------------------------------------------------------------------------

    public ForeignKeyInfosBuilder(String leftTableName) {
        this.leftTableName = leftTableName;
    }

    public List<ForeignKeyInfo> build() {
        return ls;
    }

    public void fk(String leftColumnName, String rightTableName, String rightPKColumn) {
        ls.add(new ForeignKeyInfo(leftTableName, leftColumnName, rightTableName, rightPKColumn));
    }

    //---------------------------------------------------------------------------------------------


}
