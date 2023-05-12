package com.hisa.backend.bean.response;

import com.hisa.backend.bean.model.ConsentLog;

import java.util.List;

public class ConsentLogResponse {

    public List<ConsentLog> getconsentLogs() {
        return consentLogs;
    }

    public void setconsentLogs(List<ConsentLog> consentLogs) {
        this.consentLogs = consentLogs;
    }

    List<ConsentLog> consentLogs;
    @Override
    public String toString() {
        return "ConsentLogResponse{" +
                "consentLogs=" + consentLogs +
//                ", pid='" + pid + '\'' +
                '}';
    }
}
