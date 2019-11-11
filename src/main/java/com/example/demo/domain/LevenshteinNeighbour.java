package com.example.demo.domain;

import lombok.Data;
import lombok.NonNull;
import org.neo4j.ogm.annotation.*;

@Data
@RelationshipEntity(type = "IS_LEVENSHTEIN_NEIGHBOUR_TO")
public class LevenshteinNeighbour {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    @StartNode
    private NeighbourhoodItem one;
    @NonNull
    @EndNode
    private NeighbourhoodItem two;
}