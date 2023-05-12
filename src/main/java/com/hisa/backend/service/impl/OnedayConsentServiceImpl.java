package com.hisa.backend.service.impl;

import com.hisa.backend.bean.model.OneDayConsent;
import com.hisa.backend.security.model.AuthRequest;
import com.hisa.backend.service.serviceinterface.OnedayConsentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Transactional
@Service
public class OnedayConsentServiceImpl implements OnedayConsentService {
    @Value("${his.client.id}")
    private String hisClientId;

    @Value("${his.client.secret}")
    private String hisClientSecret;
    private RestTemplate restTemplate_his;

//    String cmsBaseURL;
    public OnedayConsentServiceImpl(@Value("${cms_service.base.url}"+"/api/v1")String cmsBaseURL, RestTemplateBuilder builder){
        this.restTemplate_his= builder.rootUri(cmsBaseURL).build();

    }


    public ResponseEntity saveOnedayConsent( List<OneDayConsent> consents) {
        ResponseEntity response;

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

        String Token = restTemplate_his.postForObject(cms, request_token, String.class, "");
        headers.set("Authorization", "Bearer " + Token);

//        System.out.println(entity.get(0).toString());
        HttpEntity<?> request = new HttpEntity<>(consents, headers);
        String url ="/cms/save-voluntary-consent";
        response = restTemplate_his.postForEntity(url, request, ResponseEntity.class, "");

        return response;
    }




}
