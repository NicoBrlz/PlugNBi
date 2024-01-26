package fr.an.exprlib.metadata;

import com.google.common.collect.ImmutableList;

public record JoinRelationship(
        String leftTable,
        String rightTable,
        ImmutableList<JoinedColumn> columns) {
}
