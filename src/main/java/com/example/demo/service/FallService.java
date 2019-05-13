package com.example.demo.service;

import com.example.demo.domain.Fall;

public class FallService implements Service<Fall> {
    @Override
    public Class<Fall> getEntityType() {
        return Fall.class;
    }
}