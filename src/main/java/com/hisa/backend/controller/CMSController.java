package com.hisa.backend.controller;


import com.hisa.backend.bean.model.DelegateConsentReq;
import com.hisa.backend.bean.model.EmergencyAction;
import com.hisa.backend.bean.model.OneDayConsent;
import com.hisa.backend.bean.response.ConsentLogResponse;
import com.hisa.backend.bean.model.PendingRequest;
import com.hisa.backend.bean.response.ConsentStats;
import com.hisa.backend.bean.response.ConsentedMedResponse;
import com.hisa.backend.bean.response.PendingRequestsResponse;
import com.hisa.backend.service.serviceinterface.ConsentLogService;
import com.hisa.backend.service.serviceinterface.PendingReqService;
import com.hisa.backend.service.serviceinterface.ConsentService;
import com.hisa.backend.service.serviceinterface.GetConsentedMedDataService;
import com.hisa.backend.service.serviceinterface.GetDoctorStatsService;
import com.hisa.backend.service.serviceinterface.OnedayConsentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/api/v1/cms")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CMSController {
    private final ConsentService consentService;
    private final OnedayConsentService onedayConsentService;
    private final PendingReqService pendingReqsvc;
    private final ConsentLogService consentLogsvc;
    private final GetDoctorStatsService doctorStatsService;

    private final GetConsentedMedDataService patientMedDataService;

    public CMSController(GetConsentedMedDataService patientMedDataService,
                         PendingReqService pendingReqsvc,
                         ConsentLogService consentLogsvc,
                         OnedayConsentService onedayConsentService,
                         ConsentService consentService,
                         GetDoctorStatsService doctorStatsService
    ) {
        this.patientMedDataService=patientMedDataService;
        this.pendingReqsvc=pendingReqsvc;
        this.consentLogsvc=consentLogsvc;
        this.onedayConsentService = onedayConsentService;
        this.doctorStatsService=doctorStatsService;
        this.consentService = consentService;

    }
    @GetMapping("/{eid}/{did}/get-consented-meddata/{pid}")
    ResponseEntity<ConsentedMedResponse> getConsentedMedData(@PathVariable String eid, @PathVariable String did, @PathVariable String pid) {

//        System.out.println("========================== PID : "+pid+"==========================");
        ConsentedMedResponse PatientMedicalDetail = patientMedDataService.getConsentedData(eid,did,pid);
//        MedicalRecordResponse PatientMedicalDetail = patientMedDataService.getConsentedData(eid,did,pid);

        return ResponseEntity.status(HttpStatus.OK).body(PatientMedicalDetail);
//        List<PatientDemographics> PatientDemoDetails = patientDemoService.PatientDemographics(pid);
//        return ResponseEntity.status(HttpStatus.OK).body(PatientsVisited);
    }

    @GetMapping("/{eid}/get-pending-requests/{did}")
    ResponseEntity<PendingRequestsResponse> getPendingRequests(@PathVariable String eid, @PathVariable String did) {

//        System.out.println("========================== PID : "+pid+"==========================");
        PendingRequestsResponse pendingrequest = pendingReqsvc.getPendingRequests(eid,did);
//        MedicalRecordResponse PatientMedicalDetail = patientMedDataService.getConsentedData(eid,did,pid);

        return ResponseEntity.status(HttpStatus.OK).body(pendingrequest);
//        List<PatientDemographics> PatientDemoDetails = patientDemoService.PatientDemographics(pid);
//        return ResponseEntity.status(HttpStatus.OK).body(PatientsVisited);
    }

    @GetMapping("/{eid}/get-consent-logs/{did}")
    ResponseEntity<ConsentLogResponse> getConsentLogs(@PathVariable String eid, @PathVariable String did) {

//        System.out.println("========================== PID : "+pid+"==========================");
        ConsentLogResponse consentlogs = consentLogsvc.getConsentLogs(eid,did);
//        MedicalRecordResponse PatientMedicalDetail = patientMedDataService.getConsentedData(eid,did,pid);

        return ResponseEntity.status(HttpStatus.OK).body(consentlogs);
//        List<PatientDemographics> PatientDemoDetails = patientDemoService.PatientDemographics(pid);
//        return ResponseEntity.status(HttpStatus.OK).body(PatientsVisited);
    }



    @GetMapping("/get-doctor-stats/{did}/{eid}")
    ResponseEntity<ConsentStats> getConsentStats(@PathVariable String did,@PathVariable String eid) {

//        System.out.println("========================== PID : "+pid+"==========================");
        ResponseEntity<ConsentStats> consentStats = doctorStatsService.getDoctorConsentStats(did,eid);
//        MedicalRecordResponse PatientMedicalDetail = patientMedDataService.getConsentedData(eid,did,pid);

        return consentStats;

    }
    @PostMapping("/save-oneday-consent")
    public ResponseEntity saveOnedayConsent(@RequestBody List<OneDayConsent> consents){
        System.out.println(consents.toString());
        ResponseEntity response=null;
        try {
            response= onedayConsentService.saveOnedayConsent(consents);

            return response.ok().build();
        }catch (Exception E){
            System.out.println(E);
        }
        System.out.println(response);
        return  ResponseEntity.badRequest().body("Bad request");
//        return 5 ;
    }

    @PostMapping("/new-consent-request")
    public ResponseEntity<String> createConsent(@RequestBody PendingRequest pendingRequest){
        Integer status =  consentService.createConsent(pendingRequest);
        if(status == 1){
            return new ResponseEntity<>("Consent Request Successfully Created", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Consent Request Creation Failed", HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/create-emergency-consent")
    public ResponseEntity<String> createEmergencyConsent(@RequestBody PendingRequest pendingRequest){
        Integer status = consentService.createEmergencyConsent(pendingRequest);
        if(status==1){
            return new ResponseEntity<>("The emergency consent was created successfully",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Creation of emergency consent was unsuccessful",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/action-on-emergency-consent/{pendingrequestid}")
    public ResponseEntity<String> actionOnEmergencyConsent(@PathVariable String pendingrequestid, @RequestBody EmergencyAction action){
        Integer status = consentService.actionOnEmergencyConsent(pendingrequestid,action);
        if(status==1){
            return new ResponseEntity<>("Action on Emergency consent was successful",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Action on Emergency consent was unsuccessful",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-emergency-consent-list/{super_id}")
    public ResponseEntity<List<PendingRequest>> getEmergencyConsentList(@PathVariable String super_id){
        List<PendingRequest> emergencyPendingRequestList = consentService.getEmergencyConsentList(super_id);
        if(isNull(emergencyPendingRequestList)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emergencyPendingRequestList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(emergencyPendingRequestList);
    }

    @PostMapping("/delegate-consent")
    public ResponseEntity delegateConsent(@RequestBody DelegateConsentReq delegateConsentReq){

        Integer status = consentService.delegateConsent(delegateConsentReq);
        if(status==1){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
