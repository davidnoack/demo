package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
public class Patient implements NeighbourhoodItem<Patient> {
    @Id
    @NonNull
    @JsonProperty
    private Long lebensfallNr;
    @NonNull
    @JsonProperty
    private char geschlecht;

    @Override
    public int calculateNeighbourhoodDistanceTo(Patient other) {
        return this.geschlecht == other.geschlecht ? 0 : 1;
    }
}