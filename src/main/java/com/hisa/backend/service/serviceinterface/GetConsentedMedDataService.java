package com.hisa.backend.service.serviceinterface;

import com.hisa.backend.bean.response.ConsentedMedResponse;

public interface GetConsentedMedDataService {
    public ConsentedMedResponse getConsentedData(String eid, String did, String pid);

}
