package com.hisa.backend.security.controller;

import com.hisa.backend.security.model.AuthRequest;
import com.hisa.backend.security.response.AuthenticationResponse;
import com.hisa.backend.security.service.AuthenticationService;
import com.hisa.backend.security.service.JwtService;
import com.hisa.backend.security.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationContoller {
    private final AuthenticationService service;
    private final LogoutService logoutService;
    @Value("${cms.client.id}")
    private String cmsClientId;

    @Value("${cms.client.secret}")
    private String cmsClientSecret;
//    @Value("${patient.client.id}")
//    private String patientClientId;
//
//    @Value("${patient.client.secret}")
//    private String patientClientSecret;

    @Autowired
    private JwtService jwtService;
    @PostMapping(value = "/authenticate-cms")
    public ResponseEntity<?> cmsAuthenticate(@RequestBody AuthRequest authRequest){
System.out.println("-------- Authenticating CMS ------ ");
        if(cmsClientId.equals(authRequest.getUsername()) && cmsClientSecret.equals(authRequest.getPassword())){
            String token=jwtService.createToken(cmsClientId);
            System.out.print(token);
            return ResponseEntity.ok(token);
        }
        ResponseEntity<String> resp=new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        return resp;
    }
//    @PostMapping(value = "/authenticate-patient")
//    public ResponseEntity<?> patientAuthenticate(@RequestBody AuthRequest authRequest){
//
//        if(patientClientId.equals(authRequest.getUsername()) && patientClientSecret.equals(authRequest.getPassword())){
//            String token=jwtService.createToken(patientClientId);
//            System.out.print(token);
//            return ResponseEntity.ok(token);
//        }
//        ResponseEntity<String> resp=new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
//        return resp;
//    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthRequest request) {
        try {
            return ResponseEntity.ok(service.authenticate(request));
        }
        catch (Exception e){
            return ResponseEntity.status(403).build();
        }
}

    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponse> logout(@RequestHeader HttpHeaders headers) {
        try {
            System.out.println(headers);
            logoutService.logout(headers);

            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(403).build();
        }
    }
}
