package com.example.demo.controller;

import com.example.demo.domain.Diagnose;
import com.example.demo.domain.Fall;
import com.example.demo.domain.ICDKode;
import com.example.demo.domain.Patient;
import com.example.demo.service.DiagnoseService;
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
        return "index";
    }
}