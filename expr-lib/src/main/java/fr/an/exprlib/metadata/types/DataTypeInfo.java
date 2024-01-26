package fr.an.exprlib.metadata.types;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import fr.an.exprlib.dto.metadata.types.DataTypeDTO;
import fr.an.exprlib.dto.metadata.types.DataTypeDTO.StructFieldDTO;
import fr.an.exprlib.dto.metadata.types.DataTypeDTO.StructTypeDTO;
import fr.an.exprlib.metadata.types.StructTypeInfoBuilder.StructFieldInfoBuilder;
import fr.an.exprlib.utils.LsUtils;
import lombok.AllArgsConstructor;
import lombok.val;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * abstract class corresponding to spark org.apache.spark.sql.types.DataType
 */
public abstract class DataTypeInfo<T> {

    public final Class<T> javaClass;

    public abstract void accept(DataTypeInfoVisitor visitor);
    public abstract <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param);

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof DataTypeInfo)) {
            return false;
        }
        return equalsType((DataTypeInfo<?>) obj);
    }

    protected abstract boolean equalsType(DataTypeInfo<?> other);

    @Override
    public String toString() {
        return toSqlString();
    }

    protected abstract String toSqlString();

    //---------------------------------------------------------------------------------------------

    protected DataTypeInfo(Class<T> javaClass) {
        this.javaClass = javaClass;
    }

    public static class NullDataTypeInfo extends DataTypeInfo<Object> {
        public static final NullDataTypeInfo INSTANCE = new NullDataTypeInfo();
        private NullDataTypeInfo() { super(Object.class); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseNull(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseNull(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof NullDataTypeInfo;
        }

        @Override
        protected String toSqlString() {
            return "any"; // not real sql
        }
    }

    /** abstract base class for all atomic types:
     * boolean, char, string, byte, short, int, long, float, double, decimal, binary, datetime ...
     */
    public static abstract class AtomicTypeInfo<T> extends DataTypeInfo<T> {
        public final Type primitiveJavaType;
        protected AtomicTypeInfo(Class<T> javaType, Type primitiveJavaType) {
            super(javaType);
            this.primitiveJavaType = primitiveJavaType;
        }
    }

    /** type for Array[byte] */
    public static class BinaryTypeInfo extends AtomicTypeInfo<byte[]> {
        public static final BinaryTypeInfo INSTANCE = new BinaryTypeInfo();
        private BinaryTypeInfo() { super(byte[].class, null); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseBinary(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseBinary(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof BinaryTypeInfo;
        }

        @Override
        protected String toSqlString() {
            return "binary";
        }
    }

    /** type for Boolean */
    public static class BooleanTypeInfo extends AtomicTypeInfo<Boolean> {
        public static final BooleanTypeInfo INSTANCE = new BooleanTypeInfo();
        private BooleanTypeInfo() { super(Boolean.class, boolean.class); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseBoolean(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseBoolean(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof BooleanTypeInfo;
        }

        @Override
        protected String toSqlString() {
            return "bool";
        }
    }

    /** type for Char */
    public static class CharTypeInfo extends AtomicTypeInfo<Character> {
        public static final CharTypeInfo INSTANCE = new CharTypeInfo();
        private CharTypeInfo() { super(Character.class, char.class); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseChar(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseChar(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof CharTypeInfo;
        }
        @Override
        protected String toSqlString() {
            return "char";
        }
    }

    /** type for Boolean */
    public static class StringTypeInfo extends AtomicTypeInfo<String> {
        public static final StringTypeInfo INSTANCE = new StringTypeInfo();
        private StringTypeInfo() { super(String.class, null); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseString(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseString(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof StringTypeInfo;
        }
        @Override
        protected String toSqlString() {
            return "string";
        }
    }

    /** type for Date */
    public static class DateTypeInfo extends AtomicTypeInfo<java.time.LocalDate> {
        public static final DateTypeInfo INSTANCE = new DateTypeInfo();
        private DateTypeInfo() { super(java.time.LocalDate.class, null); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseDate(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseDate(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof DateTypeInfo;
        }
        @Override
        protected String toSqlString() {
            return "date";
        }
    }

    /** type for DateTime */
    public static class DateTimeTypeInfo extends AtomicTypeInfo<java.time.LocalDateTime> {
        public static final DateTimeTypeInfo INSTANCE = new DateTimeTypeInfo();
        private DateTimeTypeInfo() { super(java.time.LocalDateTime.class, null); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseDateTime(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseDateTime(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof DateTimeTypeInfo;
        }
        @Override
        protected String toSqlString() {
            return "datetime";
        }
    }

    /** abstract type for numerics  */
    public static abstract class NumericTypeInfo<T /*extends Number*/> extends AtomicTypeInfo<T> {
        protected NumericTypeInfo(Class<T> javaType, Type primitiveJavaType) {
            super(javaType, primitiveJavaType);
        }
    }
    /** abstract base class for Byte,Short,Int,Long */
    public static abstract class IntegralTypeInfo<T /*extends Number*/> extends NumericTypeInfo<T> {
        protected IntegralTypeInfo(Class<T> javaType, Type primitiveJavaType) {
            super(javaType, primitiveJavaType);
        }
    }

    public static class ByteTypeInfo extends IntegralTypeInfo<Byte> {
        public static final ByteTypeInfo INSTANCE = new ByteTypeInfo();
        private ByteTypeInfo() { super(Byte.class, byte.class); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseByte(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseByte(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof ByteTypeInfo;
        }
        @Override
        protected String toSqlString() {
            return "byte";
        }
    }

    public static class IntegerTypeInfo extends IntegralTypeInfo<Integer> {
        public static final IntegerTypeInfo INSTANCE = new IntegerTypeInfo();
        private IntegerTypeInfo() { super(Integer.class, int.class); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseInteger(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseInteger(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof IntegerTypeInfo;
        }
        @Override
        protected String toSqlString() {
            return "int";
        }
    }

    public static class LongTypeInfo extends IntegralTypeInfo<Long> {
        public static final LongTypeInfo INSTANCE = new LongTypeInfo();
        private LongTypeInfo() { super(Long.class, long.class); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseLong(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseLong(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof LongTypeInfo;
        }
        @Override
        protected String toSqlString() {
            return "long";
        }
    }

    public static class ShortTypeInfo extends IntegralTypeInfo<Short> {
        public static final ShortTypeInfo INSTANCE = new ShortTypeInfo();
        private ShortTypeInfo() { super(Short.class, short.class); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseShort(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseShort(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof ShortTypeInfo;
        }
        @Override
        protected String toSqlString() {
            return "short";
        }
    }

    /** abstract base class for Float,Double,Decimal */
    public static abstract class FractionalTypeInfo<T /*extends Number*/> extends NumericTypeInfo<T> {
        protected FractionalTypeInfo(Class<T> javaType, Type primitiveJavaType) {
            super(javaType, primitiveJavaType);
        }
    }

    public static class FloatTypeInfo extends FractionalTypeInfo<Float> {
        public static final FloatTypeInfo INSTANCE = new FloatTypeInfo();
        private FloatTypeInfo() { super(Float.class, float.class); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseFloat(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseFloat(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof FloatTypeInfo;
        }

        @Override
        protected String toSqlString() {
            return "float";
        }
    }

    public static class DoubleTypeInfo extends FractionalTypeInfo<Double> {
        public static final DoubleTypeInfo INSTANCE = new DoubleTypeInfo();
        private DoubleTypeInfo() { super(Double.class, double.class); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseDouble(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseDouble(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof DoubleTypeInfo;
        }
        @Override
        protected String toSqlString() {
            return "double";
        }
    }

    public static class DecimalTypeInfo extends FractionalTypeInfo<BigDecimal> {
        public static final DecimalTypeInfo INSTANCE = new DecimalTypeInfo();
        private DecimalTypeInfo() { super(BigDecimal.class, null); }
        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseDecimal(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseDecimal(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            return obj instanceof DecimalTypeInfo;
        }
        @Override
        protected String toSqlString() {
            return "decimal";
        }
    }

    //---------------------------------------------------------------------------------------------

    /**
     * StructTypeInfo composite for : struct{ field1:Type1, field2: Type2, }
     * <p></p>
     * compared to spark org.apache.spark.sql.types.StructType,
     * it also contains the corresponding Encoder : getters and setters for fields
     */
    public static class StructTypeInfo<T> extends DataTypeInfo<T> {
        public final String name;
        public final Class<T> objClass; // redundant with super.javaClass
        public final ImmutableList<StructFieldInfo<T,?>> fields;

        /** redundant with fields */
        public final ImmutableList<String> fieldNames;
        /** redundant with fields */
        public final ImmutableMap<String,StructFieldInfo<T,?>> nameToField;
        /** redundant with fields */
        public final ImmutableMap<String,Integer> nameToIndex;

        // TOADD public final Supplier<T> createMethod;

        // do not use directly, cf StructTypeInfoBuilder
        /*pp*/ StructTypeInfo(String name, Class<T> objClass,
                              LinkedHashMap<String, StructFieldInfoBuilder<T, ?>> fieldBuilders) {
            super(objClass);
            this.name = name;
            this.objClass = objClass;
            this.fields = LsUtils.mapImmutableList(fieldBuilders.values(), fb -> new StructFieldInfo<>(this,fb));
            this.fieldNames = LsUtils.mapImmutableList(fields, f -> f.name);
            this.nameToField = ImmutableMap.copyOf(LsUtils.toMap(fields, f -> f.name));
            this.nameToIndex = ImmutableMap.copyOf(LsUtils.toMapWithIndex(fieldNames));
        }

        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseStruct(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseStruct(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            if (!(obj instanceof StructTypeInfo)) {
                return false;
            }
            StructTypeInfo<?> other = (StructTypeInfo<?>) obj;
            if (!name.equals(other.name) || !objClass.equals(other.objClass)) {
                return false;
            }
            if (!fields.equals(other.fields)) {
                return false;
            }
            return true;
        }

        @Override
        protected String toSqlString() {
            StringBuilder sb = new StringBuilder();
            sb.append("struct<");
            int fieldCount = fields.size();
            for(int i = 0; i < fieldCount; i++) {
                val f = fields.get(i);
                sb.append(f.name);
                sb.append(":");
                sb.append(f.dataType.toString());
                if (i+1 < fieldCount) {
                    sb.append(",");
                }
            }
            sb.append(">");
            return sb.toString();
        }

        public StructTypeDTO toDTO() {
            return new StructTypeDTO(name, LsUtils.map(fields, f -> f.toDTO()));
        }
    }

    @AllArgsConstructor
    public static class StructFieldInfo<TObj,TField> {
        public final StructTypeInfo<TObj> parent;
        public final String name;
        public final DataTypeInfo dataType;
        public final boolean allowNullable;
        public final Function<TObj,TField> getterMethod;
        // TOADD public final BiConsumer<TObj,TField> setterMethod;
        // TOADD public final Metadata metadata;

        private StructFieldInfo(StructTypeInfo<TObj> parent, StructFieldInfoBuilder<TObj,TField> builder) {
            this.parent = parent;
            this.name = builder.name;
            this.dataType = builder.dataType;
            this.allowNullable = builder.allowNullable;
            this.getterMethod = builder.objGetterMethod;
        }

        public StructFieldDTO toDTO() {
            DataTypeDTO dateTypeDTO = DataTypeToDTOConverter.toDTO(dataType);
            return new StructFieldDTO(name, dateTypeDTO, allowNullable);
        }

    }

    //---------------------------------------------------------------------------------------------

    /**
     * MapTypeInfo composite for : Map<K,V>
     * corresponding to org.apache.spark.sql.types.MapType
     */
    public static class MapTypeInfo<K,V> extends DataTypeInfo<Map<K,V>> {
        public final DataTypeInfo<K> keyType;
        public final DataTypeInfo<V> valueType;
        public final boolean valueContainsNull;

        public MapTypeInfo(DataTypeInfo<K> keyType, DataTypeInfo<V> valueType, boolean valueContainsNull) {
            super((Class<Map<K,V>>) (Class<?>) Map.class);
            this.keyType = keyType;
            this.valueType = valueType;
            this.valueContainsNull = valueContainsNull;
        }

        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseMap(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseMap(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            if (!(obj instanceof MapTypeInfo)) {
                return false;
            }
            MapTypeInfo other = (MapTypeInfo) obj;
            if (!keyType.equals(other.keyType) || !valueType.equals(other.valueType) || valueContainsNull!=other.valueContainsNull) {
                return false;
            }
            return true;
        }

        @Override
        protected String toSqlString() {
            return "map<" + keyType.toSqlString() + "," + valueType.toSqlString() + ">";
        }
    }

    //---------------------------------------------------------------------------------------------

    /**
     * ArrayTypeInfo composite for : List<T> (or Array)
     * corresponding to org.apache.spark.sql.types.ArrayType
     */
    public static class ArrayTypeInfo<T> extends DataTypeInfo<List<T>> {
        public final DataTypeInfo<T> elementType;
        public final boolean containsNulls;

        public ArrayTypeInfo(DataTypeInfo<T> elementType, boolean containsNulls) {
            super((Class<List<T>>) (Class<?>) List.class);
            this.elementType = elementType;
            this.containsNulls = containsNulls;
        }

        @Override
        public void accept(DataTypeInfoVisitor visitor) {
            visitor.caseArray(this);
        }
        @Override
        public <TRes,TParam> TRes accept2(DataTypeInfoVisitor2<TRes,TParam> visitor, TParam param) {
            return visitor.caseArray(this, param);
        }

        @Override
        protected boolean equalsType(DataTypeInfo<?> obj) {
            if (!(obj instanceof ArrayTypeInfo)) {
                return false;
            }
            ArrayTypeInfo other = (ArrayTypeInfo) obj;
            if (!elementType.equals(other.elementType) || containsNulls!=other.containsNulls) {
                return false;
            }
            return true;
        }

        @Override
        protected String toSqlString() {
            return "array<" + elementType.toSqlString() + ">";
        }

    }

}
