package fr.an.exprlib.dto.metadata;

import fr.an.exprlib.metadata.JoinRelationship;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
public class DatabaseInfoDTO {

    public List<TableInfoDTO> tables;

    public List<JoinRelationship> otherJoinRelationShips;

}
