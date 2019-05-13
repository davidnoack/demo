package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDate;
import java.util.Set;

@Data
@NodeEntity
public class Fall {
    @Id
    @NonNull
    @JsonProperty
    private Long fallnummer;
    @NonNull
    @JsonProperty
    @EqualsAndHashCode.Exclude
    @Relationship(type = "RECEIVES_TREATMENT", direction = Relationship.INCOMING)
    private Patient patient;
    @NonNull
    @JsonProperty
    private LocalDate aufnahmeDatum;
    @JsonProperty
    @EqualsAndHashCode.Exclude
    @Relationship(type = "IS_DIAGNOSED_WITH")
    private Set<ICDKode> diagnosen;
}