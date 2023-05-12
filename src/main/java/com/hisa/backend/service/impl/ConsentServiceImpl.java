package com.hisa.backend.service.impl;

import com.hisa.backend.bean.entity.DoctorEntity;
import com.hisa.backend.bean.model.DelegateConsentReq;
import com.hisa.backend.bean.model.EmergencyAction;
import com.hisa.backend.bean.model.PendingRequest;
import com.hisa.backend.repository.DoctorEntityRepository;
import com.hisa.backend.security.model.AuthRequest;
import com.hisa.backend.service.serviceinterface.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class ConsentServiceImpl implements ConsentService {
    @Autowired
    private DoctorEntityRepository doctorEntityRepository;

    private RestTemplate restTemplate_cms;

    @Value("${cms_service.base.url}")
    private String cmsBaseURL;

    @Value("${his.client.id}")
    private String hisClientId;
    @Value("${his.client.secret}")
    private String hisClientSecret;
    public ConsentServiceImpl(@Value("${cms_service.base.url}"+"/api/v1")String cmsBaseURL, RestTemplateBuilder builder) {
        this.restTemplate_cms= builder.rootUri(cmsBaseURL).build();
    }
    @Override
    public Integer createConsent(PendingRequest pendingRequest) {
        // setting the superid
        DoctorEntity doctor = doctorEntityRepository.getByDid(pendingRequest.getRequestor_id());
        pendingRequest.setSuperid(doctor.getDoctorEntity().getDid());
        //authentication begins
        String cms ="/auth/authenticate-his";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        AuthRequest entitycred=new AuthRequest();
        entitycred.setUsername(hisClientId);
        entitycred.setPassword(hisClientSecret);
        HttpEntity<?> request_token = new HttpEntity<>(entitycred, headers);
        System.out.println(request_token.getBody());
        String Token= restTemplate_cms.postForObject(cms,request_token, String.class,"");
        headers.set("Authorization", "Bearer "+Token);
        //authentication ends
        HttpEntity<?> request = new HttpEntity<>(pendingRequest, headers);
        String url = "/cms/new-consent-request";
        ResponseEntity response= restTemplate_cms.postForEntity(url,request, ResponseEntity.class,"");
        if(response.getStatusCode()== HttpStatus.OK){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer createEmergencyConsent(PendingRequest pendingRequest) {
        // setting the superid
        DoctorEntity doctor = doctorEntityRepository.getByDid(pendingRequest.getRequestor_id());
        pendingRequest.setSuperid(doctor.getDoctorEntity().getDid());
        System.out.println(pendingRequest.toString());
        //authentication begins
        String cms="/auth/authenticate-his";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        AuthRequest entitycred=new AuthRequest();
        entitycred.setUsername(hisClientId);
        entitycred.setPassword(hisClientSecret);
        HttpEntity<?> request_token = new HttpEntity<>(entitycred, headers);
        System.out.println(request_token.getBody());
        String Token= restTemplate_cms.postForObject(cms,request_token, String.class,"");
        headers.set("Authorization", "Bearer "+Token);
        //authentication ends
        HttpEntity<?> request = new HttpEntity<>(pendingRequest, headers);
        String url="/cms/create-emergency-consent";
        ResponseEntity response= restTemplate_cms.postForEntity(url,request, ResponseEntity.class,"");
        if(response.getStatusCode()==HttpStatus.OK){
            return 1;
        }
        else{
            return 0;
        }
    }

    @Override
    public Integer actionOnEmergencyConsent(String pendingrequestid, EmergencyAction action) {
        String cms="/auth/authenticate-his";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        AuthRequest entitycred=new AuthRequest();
        entitycred.setUsername(hisClientId);
        entitycred.setPassword(hisClientSecret);
        HttpEntity<?> request_token = new HttpEntity<>(entitycred, headers);
        System.out.println(request_token.getBody());
        String Token= restTemplate_cms.postForObject(cms,request_token, String.class,"");
        headers.set("Authorization", "Bearer "+Token);
        //authentication ends
        HttpEntity<?> request = new HttpEntity<>(action, headers);
        String url="/cms/action-on-emergency-consent/"+pendingrequestid;
        ResponseEntity response= restTemplate_cms.postForEntity(url,request, ResponseEntity.class,"");
        if(response.getStatusCode() == HttpStatus.OK){
            return 1;
        }
        else{
            return 0;
        }
    }

    @Override
    public List<PendingRequest> getEmergencyConsentList(String superId) {
        //authentication begins
        String cms="/auth/authenticate-his";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        AuthRequest entitycred=new AuthRequest();
        entitycred.setUsername(hisClientId);
        entitycred.setPassword(hisClientSecret);
        HttpEntity<?> request_token = new HttpEntity<>(entitycred, headers);
        System.out.println(request_token.getBody());
        String Token= restTemplate_cms.postForObject(cms,request_token, String.class,"");
        headers.set("Authorization", "Bearer "+Token);
        //authentication ends
        HttpEntity<?> request = new HttpEntity<>("", headers);
        String url="/cms/get-emergency-pending-request-list/"+superId;
        ResponseEntity<List<PendingRequest>> response;
        response= restTemplate_cms.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<PendingRequest>>(){},"");
        if(response.getStatusCode().equals(HttpStatus.OK)){
            return response.getBody();
        }
        else{
            return null;
        }
    }

    @Override
    public Integer delegateConsent(DelegateConsentReq delegateConsentReq) {
        //setting the supervisor id and name
        DoctorEntity old_doctor = doctorEntityRepository.getByDid(delegateConsentReq.getOld_accessor_id());
        DoctorEntity new_doctor = doctorEntityRepository.getByDid(old_doctor.getDoctorEntity().getDid());
        delegateConsentReq.setNew_accessor_id(new_doctor.getDid());
        delegateConsentReq.setNew_accessor_name(new_doctor.getDname());
        //authentication begins
        String cms="/auth/authenticate-his";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        AuthRequest entitycred=new AuthRequest();
        entitycred.setUsername(hisClientId);
        entitycred.setPassword(hisClientSecret);
        HttpEntity<?> request_token = new HttpEntity<>(entitycred, headers);
        System.out.println(request_token.getBody());
        String Token= restTemplate_cms.postForObject(cms,request_token, String.class,"");
        headers.set("Authorization", "Bearer "+Token);
        //authentication ends
        HttpEntity<?> request = new HttpEntity<>(delegateConsentReq, headers);
        String url="/cms/delegate-consent";
        ResponseEntity response= restTemplate_cms.postForEntity(url,request, ResponseEntity.class,"");
        if(response.getStatusCode() == HttpStatus.OK){
            return 1;
        }
        else{
            return 0;
        }
    }
}
