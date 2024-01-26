package fr.an.exprlib.metadata.types;

import fr.an.exprlib.metadata.types.DataTypeInfo.*;

public abstract class DataTypeInfoVisitor {
    public abstract void caseNull(NullDataTypeInfo p);

    public abstract void caseStruct(StructTypeInfo<?> p);

    public abstract void caseMap(MapTypeInfo<?,?> p);

    public abstract void caseArray(ArrayTypeInfo<?> p);

    public abstract void caseBinary(BinaryTypeInfo p);

    public abstract void caseBoolean(BooleanTypeInfo p);

    public abstract void caseChar(CharTypeInfo p);
    public abstract void caseString(StringTypeInfo p);

    public abstract void caseDate(DateTypeInfo p);
    public abstract void caseDateTime(DateTimeTypeInfo p);

    public abstract void caseByte(ByteTypeInfo p);

    public abstract void caseInteger(IntegerTypeInfo p);

    public abstract void caseLong(LongTypeInfo p);

    public abstract void caseShort(ShortTypeInfo p);

    public abstract void caseFloat(FloatTypeInfo p);

    public abstract void caseDouble(DoubleTypeInfo p);

    public abstract void caseDecimal(DecimalTypeInfo p);

}
