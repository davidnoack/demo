package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
public class ICDKode implements NeighbourhoodItem<ICDKode> {
    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;
    @JsonProperty
    @NonNull
    private char icdKomponente;
    @JsonProperty
    @NonNull
    private int code;
    @JsonProperty
    @NonNull
    private int code2;
    @JsonProperty
    @NonNull
    private int icdVersion;

    @Override
    public int calculateNeighbourhoodDistanceTo(ICDKode other) {
        int distance = 0;
        if (this.icdKomponente != other.icdKomponente) distance++;
        if (this.code != other.code) distance++;
        if (this.code2 != other.code2) distance++;
        if (this.icdVersion != other.icdVersion) distance++;
        return distance;
    }
}