package com.hisa.backend.bean.response;

import com.hisa.backend.bean.entity.MedicalRecordEntity;
import com.hisa.backend.bean.model.PendingRequest;

import java.util.List;

public class PendingRequestsResponse {

    public List<PendingRequest> getpendingRequests() {
        return pendingRequests;
    }

    public void setpendingRequests(List<PendingRequest> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    List<PendingRequest> pendingRequests;
    @Override
    public String toString() {
        return "PendingReqResponse{" +
                "pendingRequests=" + pendingRequests +
//                ", pid='" + pid + '\'' +
                '}';
    }
}









