package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
public class ICDKode {
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
}