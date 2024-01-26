package fr.an.exprlib.dto.expr;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderByDTO {
    public String colName;
    public boolean descending;
}
