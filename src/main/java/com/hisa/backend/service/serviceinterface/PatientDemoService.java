package com.hisa.backend.service.serviceinterface;

import com.hisa.backend.bean.response.PatientRegResponse;
import com.hisa.backend.bean.response.PatientDemographicsResponse;

public interface PatientDemoService {
    public PatientDemographicsResponse getPatientDemographics(String pid);

    public PatientRegResponse registerPatient(PatientDemographicsResponse demographics);


}
