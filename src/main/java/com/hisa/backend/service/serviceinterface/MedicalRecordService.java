package com.hisa.backend.service.serviceinterface;

import com.hisa.backend.bean.entity.MedicalRecordEntity;
import com.hisa.backend.bean.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecordEntity> getRecords(String pid);

    List<MedicalRecordEntity> getRecordsbyRecordsId(List<String> RecordsIds,String pid);
    MedicalRecordEntity addMedicalRecords(MedicalRecord medicalRecord);

}
