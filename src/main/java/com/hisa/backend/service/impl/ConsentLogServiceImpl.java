package com.hisa.backend.service.impl;

import com.hisa.backend.bean.response.ConsentLogResponse;
import com.hisa.backend.bean.response.PendingRequestsResponse;
import com.hisa.backend.security.model.AuthRequest;
import com.hisa.backend.service.serviceinterface.ConsentLogService;
import com.hisa.backend.service.serviceinterface.PendingReqService;
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
public class ConsentLogServiceImpl implements ConsentLogService {

    @Value("${his.client.id}")
    private String hisClientId;

    @Value("${his.client.secret}")
    private String hisClientSecret;
    private RestTemplate restTemplate_cms;

    private String cmsBaseURL;

    public ConsentLogServiceImpl(@Value("${cms_service.base.url}"+"/api/v1")String cmsBaseURL, RestTemplateBuilder builder){
        this.restTemplate_cms= builder.rootUri(cmsBaseURL).build();
//        this.restTemplate_cms=builder.build();
    }
    public ConsentLogResponse getConsentLogs(String eid, String did){
        System.out.println("========================= HIS Authentication Request to CMS ==========================");

        String cms="/auth/authenticate-his";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        //get the token for communication
        AuthRequest entitycred=new AuthRequest();

        entitycred.setUsername(hisClientId);
        entitycred.setPassword(hisClientSecret);

        HttpEntity<?> request_token = new HttpEntity<>(entitycred, headers);
        System.out.println(request_token.getBody());

        String Token= restTemplate_cms.postForObject(cms,request_token, String.class,"");
        System.out.println(Token);
        //Getting the medical records
        headers.set("Authorization", "Bearer "+Token);

        cms="/his/{eid}/get-consent-logs/"+did;

        HttpEntity<?> request_records = new HttpEntity<>("", headers);
//        List<MedicalRecordResponse> finalMeddata= new ArrayList<>();
        ConsentLogResponse consentlogs= new ConsentLogResponse();
        consentlogs = restTemplate_cms.postForObject(cms,request_records, ConsentLogResponse.class ,eid,did);
//        patientDemographics= restTemplate_admin.postForObject(admin,request_records, PatientDemographicsResponse.class ,pid);
        System.out.println("CMS returned following consent logs --> \n"+ consentlogs.toString());
        return consentlogs;


    }

}
