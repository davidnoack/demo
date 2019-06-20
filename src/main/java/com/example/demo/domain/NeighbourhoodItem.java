package com.example.demo.domain;

public interface NeighbourhoodItem<T> {
    int calculateNeighbourhoodDistanceTo(T other);

    default boolean isNeighboutTo(T other, int distance) {
        return calculateNeighbourhoodDistanceTo(other) == distance;
    }

    default boolean isLevenshteinNeighbourTo(T other) {
        return isNeighboutTo(other, 1);
    }
}