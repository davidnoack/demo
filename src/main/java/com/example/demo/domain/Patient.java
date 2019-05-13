package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
public class Patient {
    @Id
    @NonNull
    @JsonProperty
    private Long lebensfallNr;
    @NonNull
    @JsonProperty
    private char geschlecht;
}