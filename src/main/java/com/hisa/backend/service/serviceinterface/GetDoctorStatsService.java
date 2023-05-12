package com.hisa.backend.service.serviceinterface;

import com.hisa.backend.bean.response.ConsentStats;
import com.hisa.backend.bean.response.ConsentedMedResponse;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;


public interface GetDoctorStatsService {

    public ResponseEntity<ConsentStats> getDoctorConsentStats(String did, String eid);

}
