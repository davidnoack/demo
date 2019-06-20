package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.neo4j.ogm.annotation.*;

@Data
@RelationshipEntity(type = "IS_LEVENSHTEIN_NEIGHBOUR_TO")
public class LevenshteinNeighbour {
    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;
    @JsonProperty
    @NonNull
    @StartNode
    private NeighbourhoodItem one;
    @JsonProperty
    @NonNull
    @EndNode
    private NeighbourhoodItem two;
}