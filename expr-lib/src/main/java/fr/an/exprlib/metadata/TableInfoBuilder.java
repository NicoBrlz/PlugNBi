package fr.an.exprlib.metadata;

import com.google.common.collect.ImmutableList;
import fr.an.exprlib.metadata.types.StructTypeInfoBuilder;

import java.util.function.Consumer;

/**
 * builder design-pattern for (immutable) TableInfo
 */
public class TableInfoBuilder<T> {

    public final String name;
    public final Class<T> objectClass;

    public final Consumer<StructTypeInfoBuilder<T>> schemaBuilderFunction;

    public final ImmutableList<String> pkColumns;

    public final Consumer<ForeignKeyInfosBuilder<T>> fkBuilderFunction;

    //---------------------------------------------------------------------------------------------

    public TableInfoBuilder(String name, Class<T> objectClass,
                            Consumer<StructTypeInfoBuilder<T>> schemaBuilderFunction,
                            String pkColumnsCommaSeparated,
                            Consumer<ForeignKeyInfosBuilder<T>> fkBuilderFunction
    ) {
        this.name = name;
        this.objectClass = objectClass;
        this.schemaBuilderFunction = schemaBuilderFunction;
        this.pkColumns = ImmutableList.copyOf(pkColumnsCommaSeparated.split(","));
        this.fkBuilderFunction = fkBuilderFunction;
    }

    public TableInfo<T> build() {
        return new TableInfo<T>(this);
    }

    //---------------------------------------------------------------------------------------------

}
