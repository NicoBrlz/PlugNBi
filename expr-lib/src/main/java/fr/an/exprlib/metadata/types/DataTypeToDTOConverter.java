package fr.an.exprlib.metadata.types;

import fr.an.exprlib.dto.metadata.types.DataTypeDTO;
import fr.an.exprlib.dto.metadata.types.DataTypeDTO.*;
import fr.an.exprlib.metadata.types.DataTypeInfo.*;
import fr.an.exprlib.utils.LsUtils;
import lombok.val;

public class DataTypeToDTOConverter {

    public static DataTypeDTO toDTO(DataTypeInfo<?> dataType) {
        val visitor = new DTOConverterDataTypeInfoVisitor();
        dataType.accept(visitor);
        return visitor.res;
    }

    //---------------------------------------------------------------------------------------------

    private static class DTOConverterDataTypeInfoVisitor extends DataTypeInfoVisitor {
        DataTypeDTO res;

        DataTypeDTO toDataTypeDTO(DataTypeInfo<?> dataType) {
            if (dataType == null) return null;
            dataType.accept(this);
            return res;
        }

        @Override
        public void caseNull(NullDataTypeInfo p) {
            this.res = new NullDataTypeDTO();
        }

        @Override
        public void caseStruct(StructTypeInfo<?> p) {
            val fields = LsUtils.map(p.fields, f -> fieldToDTO(f));
            this.res = new StructTypeDTO(p.name, fields);
        }

        public StructFieldDTO fieldToDTO(StructFieldInfo<?, ?> field) {
            DataTypeDTO dateTypeDTO = DataTypeToDTOConverter.toDTO(field.dataType);
            return new StructFieldDTO(field.name, dateTypeDTO, field.allowNullable);
        }


        @Override
        public void caseMap(MapTypeInfo p) {
            val keyType = toDataTypeDTO(p.keyType);
            val valueType = toDataTypeDTO(p.valueType);
            this.res = new MapTypeDTO(keyType, valueType, p.valueContainsNull);
        }

        @Override
        public void caseArray(ArrayTypeInfo p) {
            val elementType = toDataTypeDTO(p.elementType);
            this.res = new ArrayTypeDTO(elementType, p.containsNulls);
        }

        @Override
        public void caseBinary(BinaryTypeInfo p) {
            this.res = new BinaryTypeDTO();
        }

        @Override
        public void caseBoolean(BooleanTypeInfo p) {
            this.res = new BooleanTypeDTO();
        }

        @Override
        public void caseChar(CharTypeInfo p) {
            this.res = new CharTypeDTO();
        }

        @Override
        public void caseString(StringTypeInfo p) {
            this.res = new StringTypeDTO();
        }

        @Override
        public void caseDate(DateTypeInfo p) {
            this.res = new DateTypeDTO();
        }

        @Override
        public void caseDateTime(DateTimeTypeInfo p) {
            this.res = new DateTimeTypeDTO();
        }

        @Override
        public void caseByte(ByteTypeInfo p) {
            this.res = new ByteTypeDTO();
        }

        @Override
        public void caseInteger(IntegerTypeInfo p) {
            this.res = new IntegerTypeDTO();
        }

        @Override
        public void caseLong(LongTypeInfo p) {
            this.res = new LongTypeDTO();
        }

        @Override
        public void caseShort(ShortTypeInfo p) {
            this.res = new ShortTypeDTO();
        }

        @Override
        public void caseFloat(FloatTypeInfo p) {
            this.res = new FloatTypeDTO();
        }

        @Override
        public void caseDouble(DoubleTypeInfo p) {
            this.res = new DoubleTypeDTO();
        }

        @Override
        public void caseDecimal(DecimalTypeInfo p) {
            this.res = new DecimalTypeDTO();
        }
    }
}
