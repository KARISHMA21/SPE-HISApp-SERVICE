package com.hisa.backend.service.serviceinterface;

import com.hisa.backend.bean.response.PendingRequestsResponse;

public interface PendingReqService {

    public PendingRequestsResponse getPendingRequests(String eid, String did);
}
