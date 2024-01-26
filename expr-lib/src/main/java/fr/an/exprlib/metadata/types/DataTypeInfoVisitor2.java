package fr.an.exprlib.metadata.types;

import fr.an.exprlib.metadata.types.DataTypeInfo.*;

public abstract class DataTypeInfoVisitor2<TRes,TParam> {
    public abstract TRes caseNull(NullDataTypeInfo p, TParam param);

    public abstract TRes caseStruct(StructTypeInfo<?> p, TParam param);

    public abstract TRes caseMap(MapTypeInfo<?,?> p, TParam param);

    public abstract TRes caseArray(ArrayTypeInfo<?> p, TParam param);

    public abstract TRes caseBinary(BinaryTypeInfo p, TParam param);

    public abstract TRes caseBoolean(BooleanTypeInfo p, TParam param);

    public abstract TRes caseChar(CharTypeInfo p, TParam param);
    public abstract TRes caseString(StringTypeInfo p, TParam param);

    public abstract TRes caseDate(DateTypeInfo p, TParam param);
    public abstract TRes caseDateTime(DateTimeTypeInfo p, TParam param);

    public abstract TRes caseByte(ByteTypeInfo p, TParam param);

    public abstract TRes caseInteger(IntegerTypeInfo p, TParam param);

    public abstract TRes caseLong(LongTypeInfo p, TParam param);

    public abstract TRes caseShort(ShortTypeInfo p, TParam param);

    public abstract TRes caseFloat(FloatTypeInfo p, TParam param);

    public abstract TRes caseDouble(DoubleTypeInfo p, TParam param);

    public abstract TRes caseDecimal(DecimalTypeInfo p, TParam param);

}
