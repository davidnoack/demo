package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.neo4j.ogm.annotation.*;

@Data
@RelationshipEntity(type = "IS_DIAGNOSED_WITH")
public class Diagnose implements NeighbourhoodItem<Diagnose> {
    @Id
    @GeneratedValue
    @JsonProperty
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

    @Override
    public int calculateNeighbourhoodDistanceTo(Diagnose other) {
        int distance = 0;
        distance += icdKode.calculateNeighbourhoodDistanceTo(other.icdKode);
        distance += fall.calculateNeighbourhoodDistanceTo(other.fall);
        distance += this.isHauptdiagnose ^ other.isHauptdiagnose ? 1 : 0;
        return distance;
    }
}