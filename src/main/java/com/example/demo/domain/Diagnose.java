package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.neo4j.ogm.annotation.*;

@Data
@RelationshipEntity(type = "IS_DIAGNOSED_WITH")
public class Diagnose {
    @Id
    @GeneratedValue
    @JsonProperty
    @EqualsAndHashCode.Exclude
    private Long id;
    @JsonProperty
    @NonNull
    @EndNode
    private ICDKode icdKode;
    @JsonProperty
    @NonNull
    @StartNode
    private Fall fall;
    @JsonProperty
    @NonNull
    private boolean isHauptdiagnose;
}