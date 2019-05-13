package com.example.demo.service;

import com.example.demo.domain.ICDKode;

public class ICDKodeService implements Service<ICDKode> {
    @Override
    public Class<ICDKode> getEntityType() {
        return ICDKode.class;
    }
}