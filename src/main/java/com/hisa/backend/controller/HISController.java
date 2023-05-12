package com.hisa.backend.controller;
import com.google.gson.Gson;
import org.json.simple.*;

import com.hisa.backend.bean.entity.MedicalRecordEntity;
import com.hisa.backend.bean.model.MedicalRecord;
import com.hisa.backend.bean.response.MedicalRecordResponse;
import com.hisa.backend.bean.response.VisitRecordResponse;
import com.hisa.backend.security.service.EncryptService;
import com.hisa.backend.service.serviceinterface.MedicalRecordService;
import com.hisa.backend.service.serviceinterface.PatientsVisitedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Vector;

@RestController
@RequestMapping("/api/v1/his")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class HISController {
    private final MedicalRecordService medicalRecordService;
    private final EncryptService encryptService;
    PatientsVisitedService patientListService;
    public HISController(MedicalRecordService medicalRecordService,PatientsVisitedService patientListService,EncryptService encryptService) {
        this.medicalRecordService = medicalRecordService;
        this.patientListService = patientListService;
        this.encryptService=encryptService;
    }


    @PostMapping("/save-medical-records")
    public MedicalRecordEntity createMedicalRecords(@RequestBody MedicalRecord medicalRecord){
        System.out.println(medicalRecord.toString());
        MedicalRecordEntity medicalRecordEntity=medicalRecordService.addMedicalRecords(medicalRecord);
        return medicalRecordEntity;
//        return 5 ;
    }
    @GetMapping("/get-records/{pid}")
    public ResponseEntity<String> getEntityById(@PathVariable String pid ) {
        MedicalRecordResponse medicalRecordResponse =new MedicalRecordResponse();
//        medicalRecordResponse.setMedicalRecordEntities(medicalRecordService.getRecords(pid));
//        System.out.println(medicalRecordService.getRecords(pid));
//        String response=medicalRecordService.getRecords(pid);

        Gson gson = new Gson();

        String response = gson.toJson(medicalRecordService.getRecords(pid));
//        System.out.println("jsonCartList: " + jsonlist);

        System.out.println("--- Sending Medical records of patient: "+pid);
        return ResponseEntity.ok(encryptService.encrytData(response));

    }

    @PostMapping("/get-requested-records/{pid}")
    public ResponseEntity<MedicalRecordResponse> getMedicalRecordsByPId(@RequestParam(value = "record-ids") List<String> RecordsIds , @PathVariable String pid ) {
        MedicalRecordResponse medicalRecordResponse =new MedicalRecordResponse();
        medicalRecordResponse.setMedicalRecordEntities(medicalRecordService.getRecordsbyRecordsId(RecordsIds,pid));
//        medicalRecordResponse.setPid(pid);
        System.out.println("Sending the medical data for patient"+ pid+" having records "+RecordsIds);
        return ResponseEntity.ok(medicalRecordResponse);

    }

    @GetMapping("/{did}/view-patients-list")
    ResponseEntity< List<VisitRecordResponse>> getPatientsByDId(@PathVariable String did) {

        List<VisitRecordResponse> PatientsVisited = patientListService.PatientsVisited(did);


        return ResponseEntity.status(HttpStatus.OK).body(PatientsVisited);
    }

}
