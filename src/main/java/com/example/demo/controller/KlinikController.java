package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.service.DiagnoseService;
import com.example.demo.service.LevenshteinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class KlinikController {

    private DiagnoseService diagnoseService = new DiagnoseService();
    private LevenshteinService levenshteinService = new LevenshteinService();

    @RequestMapping("/")
    public String home() {
        return "redirect:/index.html";
    }

    @RequestMapping("/upload")
    public String uploadFile(@RequestParam("klinikdaten") MultipartFile klinikDaten) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(klinikDaten.getInputStream()));

        Set<Diagnose> diagnosesToCreate = new HashSet<>();
        Map<String, ICDKode> icdKodeMap = new HashMap<>();

        String currentLine = bufferedReader.readLine();

        while ((currentLine = bufferedReader.readLine()) != null) {

            String[] attributes = currentLine.split(",");

            boolean isHauptdiagnose = "HD".equals(attributes[3]);

            ICDKode icdKode;
            // ICD Kode plus ICD Kode Version
            String icdKodeKey = attributes[5] + attributes[6];
            if (icdKodeMap.containsKey(icdKodeKey)) {
                icdKode = icdKodeMap.get(icdKodeKey);
            } else {
                if (attributes[5].contains(".")) {
                    icdKode = new ICDKode(attributes[5].charAt(0), Integer.valueOf(attributes[5].substring(1, attributes[5].indexOf("."))), Integer.valueOf(attributes[5].substring(attributes[5].indexOf(".") + 1)), Integer.valueOf(attributes[6]));

                } else {
                    icdKode = new ICDKode(attributes[5].charAt(0), Integer.valueOf(attributes[5].substring(1)), 0, Integer.valueOf(attributes[6]));
                }
                icdKodeMap.put(icdKodeKey, icdKode);
            }
            Patient patient = new Patient(Long.valueOf(attributes[0]), attributes[4].charAt(0));
            LocalDate aufnahmeDatum = LocalDate.parse(attributes[2], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            Fall fall = new Fall(Long.valueOf(attributes[1]), patient, aufnahmeDatum);
            Diagnose diagnose = new Diagnose(icdKode, fall, isHauptdiagnose);
            diagnosesToCreate.add(diagnose);
        }
        diagnosesToCreate.forEach(diagnoseService::createOrUpdate);
        createLevenshteinRelationsFromDiagnoses(diagnosesToCreate);
        createLevenshteinRelations(new HashSet<>(icdKodeMap.values()));
        return "redirect:/index.html";
    }

    private void createLevenshteinRelations(Set<NeighbourhoodItem> possibleLevenshteinNeighbours) {
        NeighbourhoodItem[] possibleNeighboursAsArray = possibleLevenshteinNeighbours.toArray(new NeighbourhoodItem[possibleLevenshteinNeighbours.size()]);
        for (int i = 1; i < possibleNeighboursAsArray.length; i++) {
            for (int k = 0; k < possibleNeighboursAsArray.length && k < i; k++) {
                if (possibleNeighboursAsArray[i].isLevenshteinNeighbourTo(possibleNeighboursAsArray[k])) {
                    levenshteinService.createOrUpdate(new LevenshteinNeighbour(possibleNeighboursAsArray[i], possibleNeighboursAsArray[k]));
                }
            }
        }
    }

    private void createLevenshteinRelationsFromDiagnoses(Set<Diagnose> possibleLevenshteinNeighbours) {
        Diagnose[] possibleNeighboursAsArray = possibleLevenshteinNeighbours.toArray(new Diagnose[possibleLevenshteinNeighbours.size()]);
        for (int i = 1; i < possibleNeighboursAsArray.length; i++) {
            for (int k = 0; k < possibleNeighboursAsArray.length && k < i; k++) {
                Patient patient1 = possibleNeighboursAsArray[i].getFall().getPatient();
                Patient patient2 = possibleNeighboursAsArray[k].getFall().getPatient();
                if (!patient1.equals(patient2) && possibleNeighboursAsArray[i].isLevenshteinNeighbourTo(possibleNeighboursAsArray[k])) {
                    levenshteinService.createOrUpdate(new LevenshteinNeighbour(patient1, patient2));
                }
            }
        }
    }
}