package com.example.demo.domain;

import lombok.Data;
import lombok.NonNull;
import org.neo4j.ogm.annotation.*;

@Data
@RelationshipEntity(type = "IS_DIAGNOSED_WITH")
public class Diagnose implements NeighbourhoodItem<Diagnose> {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    @EndNode
    private ICDKode icdKode;
    @NonNull
    @StartNode
    private Fall fall;
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