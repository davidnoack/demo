package com.example.demo.service;

import com.example.demo.domain.LevenshteinNeighbour;

public class LevenshteinService implements Service<LevenshteinNeighbour> {
    @Override
    public Class<LevenshteinNeighbour> getEntityType() {
        return LevenshteinNeighbour.class;
    }
}