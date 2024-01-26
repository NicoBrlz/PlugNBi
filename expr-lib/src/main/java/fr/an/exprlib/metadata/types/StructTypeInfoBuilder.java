package fr.an.exprlib.metadata.types;

import fr.an.exprlib.metadata.types.DataTypeInfo.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.function.Function;

@RequiredArgsConstructor
public class StructTypeInfoBuilder<T> {
    protected final String name;
    protected final Class<T> objClass;
    protected final LinkedHashMap<String, StructFieldInfoBuilder<T,?>> fields = new LinkedHashMap<>();

    public static class StructFieldInfoBuilder<TObj, TField> {

        public final String name;
        public final DataTypeInfo<TField> dataType;
        public final boolean allowNullable;
        public final Function<TObj, TField> objGetterMethod;

        public StructFieldInfoBuilder(String name, DataTypeInfo<TField> dataType,
                                      boolean allowNullable,
                                      Function<TObj, TField> objGetterMethod) {
            this.name = name;
            this.dataType = dataType;
            this.allowNullable = allowNullable;
            this.objGetterMethod = objGetterMethod;
        }
    }

    //---------------------------------------------------------------------------------------------

    public StructTypeInfo<T> build() {
        return new StructTypeInfo(name, objClass, fields);
    }

    public void addInt(String name, Function<T,Integer> getter) {
        add(name, IntegerTypeInfo.INSTANCE, false, getter);
    }
    public void addIntegerWrapper(String name, Function<T,Integer> getter) {
        add(name, IntegerTypeInfo.INSTANCE, true, getter);
    }
    public void addString(String name, Function<T,String> getter) {
        add(name, StringTypeInfo.INSTANCE, false, getter);
    }
    public void addDouble(String name, Function<T,Double> getter) {
        add(name, DoubleTypeInfo.INSTANCE, false, getter);
    }
    public void addDoubleWrapper(String name, boolean allowNulls, Function<T,Double>getter) {
        add(name, DoubleTypeInfo.INSTANCE, allowNulls, getter);
    }
    // TOADD Instant, LocalDate, ..
    public void addDateTime(String name, Function<T, LocalDateTime> getter) {
        add(name, DateTimeTypeInfo.INSTANCE, false, getter);
    }
    public void addLocalDate(String name, Function<T, LocalDate> getter) {
        add(name, DateTypeInfo.INSTANCE, false, getter);
    }
    // TOADD addBooleanWrapper
    public void addBool(String name, Function<T,Boolean> getter) {
        add(name, BooleanTypeInfo.INSTANCE, false, getter);
    }
    public <TField> void add(String name, DataTypeInfo<TField> dataType, boolean allowNullable, Function<T,TField> getter) {
        add(new StructFieldInfoBuilder<T,TField>(name, dataType, allowNullable, getter));
    }

    public void add(StructFieldInfoBuilder<T,?> col) {
        fields.put(col.name, col);
    }

}
