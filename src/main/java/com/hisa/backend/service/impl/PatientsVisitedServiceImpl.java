package com.hisa.backend.service.impl;


import com.hisa.backend.bean.entity.VisitEntity;
import com.hisa.backend.bean.response.VisitRecordResponse;
import com.hisa.backend.repository.VisitRecordsRepository;
import com.hisa.backend.service.serviceinterface.PatientsVisitedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class PatientsVisitedServiceImpl implements PatientsVisitedService {

    @Autowired
    private VisitRecordsRepository visitRecordsRepository;


    public  List<VisitRecordResponse> PatientsVisited(String did) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<VisitEntity> records = null;
        List<VisitRecordResponse> finalRecordsList=new ArrayList<>();
        records = visitRecordsRepository.getRecords(did);
        System.out.println(records);
        System.out.println("=======================================");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (final VisitEntity m : records) {
            VisitRecordResponse finalRecords = new VisitRecordResponse();
            System.out.println(m);
            finalRecords.setDid(m.getDid_pid().getDid());
            finalRecords.setPid(m.getDid_pid().getPid());
            finalRecords.setPatientName(m.getPatientName());
            String formattedDate = dateFormat.format(m.getLastVisited());
//            try {
//            Date trimmedDate = dateFormat.parse(formattedDate);
//            System.out.println("trimmedDate ---"+trimmedDate);
                finalRecords.setLastVisited(formattedDate);

//            } catch (ParseException e) {
//                // handle the exception appropriately
//                finalRecords.setLastVisited(null);
//            }

            finalRecordsList.add(finalRecords);
//        System.out.println(finalRecords);
//        return finalRecords;
        }

        System.out.println(finalRecordsList);
        return finalRecordsList;

    }
}