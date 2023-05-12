package com.hisa.backend.controller;


import com.hisa.backend.bean.response.PatientRegResponse;
import com.hisa.backend.bean.response.PatientDemographicsResponse;
import com.hisa.backend.service.serviceinterface.PatientDemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AdminController {
    PatientDemoService patientDemoService;


    public AdminController(PatientDemoService patientDemoService

    ) {
        this.patientDemoService = patientDemoService;

    }


    @GetMapping("/{eid}/{did}/get-patient-details/{pid}")
    ResponseEntity<PatientDemographicsResponse> getPatientDetailsByDId(@PathVariable String eid, @PathVariable String did, @PathVariable String pid) {

//        System.out.println("========================== PID : "+pid+"==========================");
        PatientDemographicsResponse PatientDemoDetail = patientDemoService.getPatientDemographics(pid);
//        MedicalRecordResponse PatientMedicalDetail = patientMedDataService.getConsentedData(eid,did,pid);

        return ResponseEntity.status(HttpStatus.OK).body(PatientDemoDetail);
//        List<PatientDemographics> PatientDemoDetails = patientDemoService.PatientDemographics(pid);
//        return ResponseEntity.status(HttpStatus.OK).body(PatientsVisited);
    }

    @PostMapping("/patient-registration")
    ResponseEntity registerPatient(@RequestBody PatientDemographicsResponse patientDemo){
        System.out.println(patientDemo.toString());
        PatientRegResponse response=new PatientRegResponse();
        response =patientDemoService.registerPatient(patientDemo);
        System.out.println("Sending Response "+response.getPid());
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(response);

//        return 5 ;
    }
}
