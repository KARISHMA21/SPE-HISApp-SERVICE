package com.hisa.backend.service.serviceinterface;

import com.hisa.backend.bean.response.ConsentLogResponse;
import com.hisa.backend.bean.response.PendingRequestsResponse;

public interface ConsentLogService {

    public ConsentLogResponse getConsentLogs(String eid, String did);
}
