package fr.an.exprlib.metadata;

import fr.an.exprlib.dto.metadata.TableInfoDTO;
import fr.an.exprlib.metadata.types.DataTypeInfo.StructTypeInfo;
import fr.an.exprlib.metadata.types.StructTypeInfoBuilder;
import fr.an.exprlib.utils.LsUtils;
import lombok.val;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class TableInfo<T> {

    public final String name;

    public final StructTypeInfo<T> schema;

    protected final List<String> pkColumns;
    protected final List<ForeignKeyInfo> foreignKeyInfos;

    //---------------------------------------------------------------------------------------------

    /*pp*/ TableInfo(TableInfoBuilder<T> builder) {
        this.name = builder.name;

        StructTypeInfoBuilder<T> schemaBuilder = new StructTypeInfoBuilder<T>(name, builder.objectClass);
        builder.schemaBuilderFunction.accept(schemaBuilder);
        this.schema = schemaBuilder.build();

        this.pkColumns = Collections.unmodifiableList(builder.pkColumns);

        ForeignKeyInfosBuilder<T> fkBuilder = new ForeignKeyInfosBuilder<T>(name);
        builder.fkBuilderFunction.accept(fkBuilder);
        this.foreignKeyInfos = Collections.unmodifiableList(fkBuilder.build());
    }

    public TableInfoDTO toDTO() {
        return new TableInfoDTO(name, schema.toDTO(), pkColumns, LsUtils.map(foreignKeyInfos, x -> x.toDTO()));
    }

    public Function<T,?> resolveObjectEvalFunc(String expr) {
        val field = schema.nameToField.get(expr);
        if (field == null) {
            throw new IllegalArgumentException("field not found by name : '" + expr + "' (and extended expression not supported yet)");
        }
        return field.getterMethod;
    }

}
