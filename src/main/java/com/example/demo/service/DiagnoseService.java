package com.example.demo.service;

import com.example.demo.domain.Diagnose;

public class DiagnoseService implements Service<Diagnose> {
    @Override
    public Class<Diagnose> getEntityType() {
        return Diagnose.class;
    }
}