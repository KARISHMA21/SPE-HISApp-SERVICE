package com.hisa.backend.bean.response;

import com.hisa.backend.bean.model.FinalRecords;

import java.util.List;

public class ConsentedMedResponse {


    public List<FinalRecords> getFinalRecordsList() {
        return finalRecordsList;
    }

    @Override
    public String toString() {
        return "MedicalRecordResponse{" +
                "finalRecordsList=" + finalRecordsList +
                '}';
    }

    public void setFinalRecordsList(List<FinalRecords> finalRecordsList) {
        this.finalRecordsList = finalRecordsList;
    }

    List<FinalRecords> finalRecordsList;



}
