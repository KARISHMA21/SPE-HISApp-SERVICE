package com.hisa.backend.bean.response;

import com.hisa.backend.bean.entity.MedicalRecordEntity;

import java.util.List;

public class MedicalRecordResponse {


    public List<MedicalRecordEntity> getMedicalRecordEntities() {
        return medicalRecordEntities;
    }

    public void setMedicalRecordEntities(List<MedicalRecordEntity> medicalRecordEntities) {
        this.medicalRecordEntities = medicalRecordEntities;
    }

    List<MedicalRecordEntity> medicalRecordEntities;
}
