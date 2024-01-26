package fr.an.exprlib.dto.metadata;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
public class DatasetDTO {
    public List<RowDTO> rows;
    // TOADD ... pagination
}
