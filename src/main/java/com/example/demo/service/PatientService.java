package com.example.demo.service;

import com.example.demo.domain.Patient;

public class PatientService implements Service<Patient> {
    @Override
    public Class<Patient> getEntityType() {
        return Patient.class;
    }
}