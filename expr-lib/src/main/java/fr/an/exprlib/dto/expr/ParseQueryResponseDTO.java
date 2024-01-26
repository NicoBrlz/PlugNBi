package fr.an.exprlib.dto.expr;

import fr.an.exprlib.dto.QueryRequestDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor
public class ParseQueryResponseDTO {

    public String parsedToJson;

    public QueryRequestDTO query;

}
