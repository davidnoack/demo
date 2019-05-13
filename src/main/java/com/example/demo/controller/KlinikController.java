package com.example.demo.controller;

import com.example.demo.domain.Diagnose;
import com.example.demo.domain.Fall;
import com.example.demo.domain.ICDKode;
import com.example.demo.domain.Patient;
import com.example.demo.service.DiagnoseService;
import com.example.demo.service.FallService;
import com.example.demo.service.ICDKodeService;
import com.example.demo.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

@Controller
public class KlinikController {

    private DiagnoseService diagnoseService = new DiagnoseService();
    private FallService fallService = new FallService();
    private ICDKodeService icdKodeService = new ICDKodeService();
    private PatientService patientService = new PatientService();

    @RequestMapping("/upload")
    public String uploadFile(@RequestParam("klinikdaten") MultipartFile klinikDaten) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(klinikDaten.getInputStream()));
        Set<ICDKode> icdKodesToCreate = new HashSet<>();
        Set<Patient> clientsToCreate = new HashSet<>();
        Set<Fall> casesToCreate = new HashSet<>();
        Set<Diagnose> diagnosesToCreate = new HashSet<>();
        String currentLine = bufferedReader.readLine();
        while ((currentLine = bufferedReader.readLine()) != null) {
            String[] attributes = currentLine.split(",");
            boolean isHauptdiagnose = "HD".equals(attributes[3]);
            ICDKode icdKode;
            if (attributes[5].contains(".")) {
                icdKode = new ICDKode(attributes[5].charAt(0), Integer.valueOf(attributes[5].substring(1, attributes[5].indexOf("."))), Integer.valueOf(attributes[5].substring(attributes[5].indexOf(".") + 1)), Integer.valueOf(attributes[6]));

            } else {
                icdKode = new ICDKode(attributes[5].charAt(0), Integer.valueOf(attributes[5].substring(1)), 0, Integer.valueOf(attributes[6]));
            }
            icdKodesToCreate.add(icdKode);
            Patient patient = new Patient(Long.valueOf(attributes[0]), attributes[4].charAt(0));
            clientsToCreate.add(patient);
            LocalDate aufnahmeDatum = LocalDate.parse(attributes[2], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            Fall fall = new Fall(Long.valueOf(attributes[1]), patient, aufnahmeDatum);
            casesToCreate.add(fall);
            Diagnose diagnose = new Diagnose(icdKode, fall, isHauptdiagnose);
            diagnosesToCreate.add(diagnose);
        }
        icdKodesToCreate.forEach(icdKodeService::createOrUpdate);
        clientsToCreate.forEach(patientService::createOrUpdate);
        casesToCreate.forEach(fallService::createOrUpdate);
        diagnosesToCreate.forEach(diagnoseService::createOrUpdate);
        return "index";
    }
}