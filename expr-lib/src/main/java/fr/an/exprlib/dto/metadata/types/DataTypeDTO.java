package fr.an.exprlib.dto.metadata.types;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import fr.an.exprlib.dto.metadata.types.DataTypeDTO.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * abstract class corresponding to DataTypeDTO,
 *  (itself corresponding to spark org.apache.spark.sql.types.DataType
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes(value= {
        @JsonSubTypes.Type(name="null", value = NullDataTypeDTO.class),
        @JsonSubTypes.Type(name="binary", value = BinaryTypeDTO.class),
        @JsonSubTypes.Type(name="bool", value = BooleanTypeDTO.class),
        @JsonSubTypes.Type(name="string", value = StringTypeDTO.class),
        @JsonSubTypes.Type(name="date", value = DateTypeDTO.class),
        @JsonSubTypes.Type(name="datetime", value = DateTimeTypeDTO.class),
        @JsonSubTypes.Type(name="char", value = CharTypeDTO.class),
        @JsonSubTypes.Type(name="byte", value = ByteTypeDTO.class),
        @JsonSubTypes.Type(name="short", value = ShortTypeDTO.class),
        @JsonSubTypes.Type(name="int", value = IntegerTypeDTO.class),
        @JsonSubTypes.Type(name="long", value = LongTypeDTO.class),
        @JsonSubTypes.Type(name="float", value = FloatTypeDTO.class),
        @JsonSubTypes.Type(name="double", value = DoubleTypeDTO.class),
        @JsonSubTypes.Type(name="decimal", value = DecimalTypeDTO.class),
        @JsonSubTypes.Type(name="struct", value = StructTypeDTO.class),
        @JsonSubTypes.Type(name="map", value = MapTypeDTO.class),
        @JsonSubTypes.Type(name="array", value = ArrayTypeDTO.class)
})
public abstract class DataTypeDTO {

    public abstract void accept(DataTypeDTOVisitor visitor);

    //---------------------------------------------------------------------------------------------

    public static class NullDataTypeDTO extends DataTypeDTO {

        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseNull(this);
        }
    }

    /** abstract base class for all atomic types:
     * boolean, char, string, byte, short, int, long, float, double, decimal, binary, datetime ...
     */
    public static abstract class AtomicTypeDTO extends DataTypeDTO {
    }

    /** type for Array[byte] */
    public static class BinaryTypeDTO extends AtomicTypeDTO {
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseBinary(this);
        }
    }

    /** type for Boolean */
    public static class BooleanTypeDTO extends AtomicTypeDTO {
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseBoolean(this);
        }
    }

    /** type for Char */
    public static class CharTypeDTO extends AtomicTypeDTO {
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseChar(this);
        }
    }

    /** type for String */
    public static class StringTypeDTO extends AtomicTypeDTO {
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseString(this);
        }
    }

    /** type for LocalDateTypeDTO */
    public static class DateTypeDTO extends AtomicTypeDTO {
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseLocalDate(this);
        }
    }

    /** type for DateTime */
    public static class DateTimeTypeDTO extends AtomicTypeDTO {
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseDateTime(this);
        }
    }

    /** abstract type for numerics  */
    public static abstract class NumericTypeDTO extends AtomicTypeDTO {
    }
    /** abstract base class for Byte,Short,Int,Long */
    public static abstract class IntegralTypeDTO extends NumericTypeDTO {
    }

    public static class ByteTypeDTO extends IntegralTypeDTO {
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseByte(this);
        }
    }
    public static class IntegerTypeDTO extends IntegralTypeDTO {
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseInteger(this);
        }
    }
    public static class LongTypeDTO extends IntegralTypeDTO {
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseLong(this);
        }
    }
    public static class ShortTypeDTO extends IntegralTypeDTO {
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseShort(this);
        }
    }

    /** abstract base class for Float,Double,Decimal */
    public static abstract class FractionalTypeDTO extends NumericTypeDTO {
    }

    public static class FloatTypeDTO extends FractionalTypeDTO {
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseFloat(this);
        }
    }

    public static class DoubleTypeDTO extends FractionalTypeDTO {
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseDouble(this);
        }
    }

    public static class DecimalTypeDTO extends FractionalTypeDTO {
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseDecimal(this);
        }
    }

    //---------------------------------------------------------------------------------------------

    /**
     * StructTypeDTO composite for : struct{ field1:Type1, field2: Type2, }
     * <p></p>
     * compared to spark org.apache.spark.sql.types.StructType.
     * it also contains the corresponding Encoder : getters and setters for fields
     */
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StructTypeDTO extends DataTypeDTO {
        public String name;
        public List<StructFieldDTO> fields;

        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseStruct(this);
        }
    }

    @NoArgsConstructor @AllArgsConstructor
    public static class StructFieldDTO {
        public String name;
        public DataTypeDTO dataType;
        public boolean nullable;
        // public final Metadata metadata;

        public void accept(DataTypeDTOVisitor visitor, StructTypeDTO parent) {
            visitor.caseStructField(parent, this);
        }
    }

    //---------------------------------------------------------------------------------------------

    /**
     * MapTypeDTO composite for : Map<K,V>
     */
    @NoArgsConstructor @AllArgsConstructor
    public static class MapTypeDTO extends DataTypeDTO {
        public DataTypeDTO keyType;
        public DataTypeDTO valueType;
        public boolean valueContainsNull;
        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseMap(this);
        }
    }

    //---------------------------------------------------------------------------------------------

    /**
     * ArrayTypeDTO composite for : List<T> (or Array)
     * corresponding to org.apache.spark.sql.types.ArrayType
     */
    @NoArgsConstructor @AllArgsConstructor
    public static class ArrayTypeDTO extends DataTypeDTO {
        public DataTypeDTO elementType;
        public boolean containsNull;

        @Override
        public void accept(DataTypeDTOVisitor visitor) {
            visitor.caseArray(this);
        }

    }

}
