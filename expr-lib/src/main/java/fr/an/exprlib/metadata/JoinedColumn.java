package fr.an.exprlib.metadata;

public record JoinedColumn(
        String leftColumn,
        String rightColumn) {
}
