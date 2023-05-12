package com.hisa.backend.service.impl;

import com.hisa.backend.bean.response.PatientRegResponse;
import com.hisa.backend.bean.response.PatientDemographicsResponse;
import com.hisa.backend.repository.VisitRecordsRepository;
import com.hisa.backend.security.model.AuthRequest;
import com.hisa.backend.service.serviceinterface.PatientDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Transactional
@Service
public class PatientDemoServiceImpl implements PatientDemoService {

    @Value("${his.client.id}")
    private String hisClientId;

    @Value("${his.client.secret}")
    private String hisClientSecret;

    @Value("${entity_id}")
    private String eid;
    private RestTemplate restTemplate_admin;
//    private RestTemplate restTemplate_his;
@Autowired
private VisitRecordsRepository visitRecordsRepository;

    public PatientDemoServiceImpl(@Value("${admin_service.base.url}"+"/api/v1")String adminBaseURL, RestTemplateBuilder builder){
        this.restTemplate_admin= builder.rootUri(adminBaseURL).build();
//
    }

    public PatientDemographicsResponse getPatientDemographics(String pid){
//        System.out.println("========================== PID : "+pid+"==========================");
        System.out.println("========================== HIS Authentication Request to admin ==========================");

        String admin="/auth/authenticate-his";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        //get the token for communication
        AuthRequest entitycred=new AuthRequest();

        entitycred.setUsername(hisClientId);
        entitycred.setPassword(hisClientSecret);

        HttpEntity<?> request_token = new HttpEntity<>(entitycred, headers);
        System.out.println(request_token.getBody());

        String Token= restTemplate_admin.postForObject(admin,request_token, String.class,"");
        System.out.println("Admin returned Token --> \n"+Token);
        //Getting the medical records

        headers.set("Authorization", "Bearer "+Token);

        admin="/his/get-patient-demographics/"+pid;
//        List<EntityRes> entity =  adminResponse;


        HttpEntity<?> request_records = new HttpEntity<>("", headers);
//        patientDemographics= restTemplate_admin.postForEntity(admin,request_records,PatientDemographicsResponse.class ,pid);

        PatientDemographicsResponse patientDemographics =new PatientDemographicsResponse();
        patientDemographics= restTemplate_admin.postForObject(admin,request_records, PatientDemographicsResponse.class ,pid);
    System.out.println("The received Demographic details are --> \n"+patientDemographics.toString());
        visitRecordsRepository.processprofileUpdates(patientDemographics.getPid(),patientDemographics.getName());

//        patientDemographics= restTemplate_admin.getForObject("/his/get-patient-demographics/{pid}", PatientDemographicsResponse.class ,pid);
           return patientDemographics;
    }

    public PatientRegResponse registerPatient(PatientDemographicsResponse demographics){
//        System.out.println("========================== PID : "+pid+"==========================");
        System.out.println("========================== HIS Authentication Request to admin ==========================");

        String admin="/auth/authenticate-his";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        //get the token for communication
        AuthRequest entitycred=new AuthRequest();

        entitycred.setUsername(hisClientId);
        entitycred.setPassword(hisClientSecret);

        HttpEntity<?> request_token = new HttpEntity<>(entitycred, headers);
        System.out.println(request_token.getBody());

        String Token= restTemplate_admin.postForObject(admin,request_token, String.class,"");
        System.out.println("Admin returned Token --> \n"+Token);
        //Getting the medical records
        headers.set("Authorization", "Bearer "+Token);


        System.out.println(eid+" is sending patient reg request ");

        admin="/his/"+eid+"/patient-register";
//        List<EntityRes> entity =  adminResponse;


        HttpEntity<?> request_records = new HttpEntity<>(demographics, headers);
        System.out.println(request_records.getBody());
//        patientDemographics= restTemplate_admin.postForEntity(admin,request_records,PatientDemographicsResponse.class ,pid);
System.out.println(request_records.toString());

        PatientRegResponse response= restTemplate_admin.postForObject(admin,request_records,  PatientRegResponse.class,"");
        System.out.println("The received response for patient registration request --> \n"+response.getPid());

//        patientDemographics= restTemplate_admin.getForObject("/his/get-patient-demographics/{pid}", PatientDemographicsResponse.class ,pid);
        return response;
    }



}
