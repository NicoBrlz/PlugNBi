package fr.an.exprlib.dto.metadata;

import fr.an.exprlib.dto.metadata.types.DataTypeDTO.StructTypeDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
public class TableInfoDTO {

    public String name;

    public StructTypeDTO schema;

    public List<String> pkColumns;

    public List<ForeignKeyInfoDTO> foreignKeyInfos;

}
