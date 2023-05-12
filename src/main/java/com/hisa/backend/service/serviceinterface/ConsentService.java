package com.hisa.backend.service.serviceinterface;

import com.hisa.backend.bean.model.DelegateConsentReq;
import com.hisa.backend.bean.model.EmergencyAction;
import com.hisa.backend.bean.model.PendingRequest;

import java.util.List;

public interface ConsentService {

    Integer createConsent(PendingRequest pendingRequest);

    Integer createEmergencyConsent(PendingRequest pendingRequest);

    Integer actionOnEmergencyConsent(String pendingrequestid, EmergencyAction action);

    List<PendingRequest> getEmergencyConsentList(String superId);

    Integer delegateConsent(DelegateConsentReq delegateConsentReq);
}
