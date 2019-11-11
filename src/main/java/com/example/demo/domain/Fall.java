package com.example.demo.domain;

import lombok.Data;
import lombok.NonNull;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDate;

@Data
@NodeEntity
public class Fall implements NeighbourhoodItem<Fall> {
    @Id
    @NonNull
    private Long fallnummer;
    @NonNull
    @Relationship(type = "RECEIVES_TREATMENT", direction = Relationship.INCOMING)
    private Patient patient;
    @NonNull
    private LocalDate aufnahmeDatum;

    @Override
    public int calculateNeighbourhoodDistanceTo(Fall other) {
        int distance = 0;
        distance += patient.calculateNeighbourhoodDistanceTo(other.patient);
        if (this.aufnahmeDatum.compareTo(other.aufnahmeDatum) != 0) distance++;
        return distance;
    }
}