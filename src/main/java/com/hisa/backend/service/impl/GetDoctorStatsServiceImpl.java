package com.hisa.backend.service.impl;

import com.hisa.backend.bean.response.ConsentStats;
import com.hisa.backend.repository.VisitRecordsRepository;
import com.hisa.backend.security.model.AuthRequest;
import com.hisa.backend.service.serviceinterface.GetDoctorStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@Service
public class GetDoctorStatsServiceImpl implements GetDoctorStatsService {
    @Autowired
    private VisitRecordsRepository visitRecordsRepository;
    @Value("${his.client.id}")
    private String hisClientId;

    @Value("${his.client.secret}")
    private String hisClientSecret;
    private RestTemplate restTemplate_cms;

    private String cmsBaseURL;
    public GetDoctorStatsServiceImpl(@Value("${cms_service.base.url}"+"/api/v1")String cmsBaseURL, RestTemplateBuilder builder){
        this.restTemplate_cms= builder.rootUri(cmsBaseURL).build();
//        this.restTemplate_cms=builder.build();
    }

    public  ResponseEntity<ConsentStats> getDoctorConsentStats(String did, String eid){


        ResponseEntity<ConsentStats> response;
        ConsentStats consentStats=new ConsentStats();

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("did", did);
        uriVariables.put("eid", eid);
        String cms = "/auth/authenticate-his";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        //get the token for communication
        AuthRequest entitycred = new AuthRequest();

        entitycred.setUsername(hisClientId);
        entitycred.setPassword(hisClientSecret);

//        MultiValueMap<String, String> body = new LinkedMultiValueMap<String,String>();

//        body.add("username", adminClientId);
//        body.add("password", adminClientSecret);
        HttpEntity<?> request_token = new HttpEntity<>(entitycred, headers);
        System.out.println(request_token.getBody());

        String Token = restTemplate_cms.postForObject(cms, request_token, String.class, "");

        headers.set("Authorization", "Bearer " + Token);
        HttpEntity<?> request_records = new HttpEntity<>("", headers);
        String url="/cms/get-doctor-stats/{did}/{eid}";
        response = restTemplate_cms.exchange(url, HttpMethod.GET, request_records, new ParameterizedTypeReference<ConsentStats>() {
        }, uriVariables);
        BigInteger totalPatients=visitRecordsRepository.getPatientCount(did);
         consentStats=response.getBody();
         consentStats.setTotalpatients(totalPatients);

        return ResponseEntity.ok().body(consentStats);
    }


}
