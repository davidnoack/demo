package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NodeEntity
public class ICDKode {
    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;
    @JsonProperty
    @NonNull
    @EqualsAndHashCode.Include
    private char icdKomponente;
    @JsonProperty
    @NonNull
    @EqualsAndHashCode.Include
    private int code;
    @JsonProperty
    @NonNull
    @EqualsAndHashCode.Include
    private int code2;
    @JsonProperty
    @NonNull
    @EqualsAndHashCode.Include
    private int icdVersion;
    @JsonProperty
    @EqualsAndHashCode.Exclude
    @Relationship(type = "IS_DIAGNOSED_WITH", direction = Relationship.INCOMING)
    private Set<Fall> zugehoerigeFaelle;
}