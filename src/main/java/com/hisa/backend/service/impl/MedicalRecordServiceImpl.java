package com.hisa.backend.service.impl;

import com.hisa.backend.bean.entity.DoctorEntity;
import com.hisa.backend.bean.entity.MedicalRecordEntity;
import com.hisa.backend.bean.model.MedicalRecord;
import com.hisa.backend.repository.MedicalRecordsRepository;
import com.hisa.backend.service.serviceinterface.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Transactional
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;


    public List<MedicalRecordEntity> getRecords(String pid) {
        List<MedicalRecordEntity> records = null;

//        String q="select e from entity_reg  e JOIN entity_patient_mapping em on e.eid=em.eid  where em.pid="+pid;
        records = medicalRecordsRepository.getRecords(pid);

        return records;
    }

    public List<MedicalRecordEntity> getRecordsbyRecordsId(List<String> RecordsIds,String pid)
    {
        List<MedicalRecordEntity> records=null;
        records= medicalRecordsRepository.getRecordsbyRecordsIds(RecordsIds,pid);
        System.out.println(records);
        return records;
    }
    public MedicalRecordEntity addMedicalRecords(MedicalRecord medicalRecord) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
            medicalRecordEntity.setPid(medicalRecord.getPid());
            medicalRecordEntity.setDescription(medicalRecord.getDescription());
            medicalRecordEntity.setTag1(medicalRecord.getTag1());
            medicalRecordEntity.setTag2(medicalRecord.getTag2());
            medicalRecordEntity.setTag3(medicalRecord.getTag3());
            medicalRecordEntity.setGen_date(simpleDateFormat.parse(medicalRecord.getGen_date()));
            medicalRecordEntity.setRid(medicalRecord.getRid());
            medicalRecordEntity.setType(medicalRecord.getType());
            DoctorEntity doctorEntity = medicalRecordsRepository.getDoctorInfo(medicalRecord.getGen_did());
            medicalRecordEntity.setDoctorEntity(doctorEntity);
            System.out.println(medicalRecordEntity.toString());
            medicalRecordsRepository.save(medicalRecordEntity);
            return medicalRecordEntity;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;

        }
}
