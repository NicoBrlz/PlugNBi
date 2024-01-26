package fr.an.exprlib.dto.metadata.types;


import fr.an.exprlib.dto.metadata.types.DataTypeDTO.*;

public abstract class DataTypeDTOVisitor {
    public abstract void caseNull(NullDataTypeDTO p);

    public abstract void caseStruct(StructTypeDTO p);
    public abstract void caseStructField(StructTypeDTO parent, StructFieldDTO field);

    public abstract void caseMap(MapTypeDTO p);

    public abstract void caseArray(ArrayTypeDTO p);

    public abstract void caseBinary(BinaryTypeDTO p);

    public abstract void caseBoolean(BooleanTypeDTO p);

    public abstract void caseChar(CharTypeDTO p);

    public abstract void caseString(StringTypeDTO p);

    public abstract void caseLocalDate(DateTypeDTO p);

    public abstract void caseDateTime(DateTimeTypeDTO p);

    public abstract void caseByte(ByteTypeDTO p);

    public abstract void caseInteger(IntegerTypeDTO p);

    public abstract void caseLong(LongTypeDTO p);

    public abstract void caseShort(ShortTypeDTO p);

    public abstract void caseFloat(FloatTypeDTO p);

    public abstract void caseDouble(DoubleTypeDTO p);

    public abstract void caseDecimal(DecimalTypeDTO p);

}
