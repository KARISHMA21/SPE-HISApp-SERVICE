package com.hisa.backend.bean.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data //for getters and setters
@Table(name="doctor")
public class DoctorEntity {
    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public DoctorEntity getDoctorEntity() {
        return doctorEntity;
    }

    public void setDoctorEntity(DoctorEntity doctorEntity) {
        this.doctorEntity = doctorEntity;
    }

    @Id
    private String did;
    private String dname;
    private String specialization;

//    public Set<MedicalRecordEntity> getMedicalRecordEntities() {
//        return medicalRecordEntities;
//    }
//
//    public void setMedicalRecordEntities(Set<MedicalRecordEntity> medicalRecordEntities) {
//        this.medicalRecordEntities = medicalRecordEntities;
//    }

    @ManyToOne
    @JoinColumn(name="super_did", nullable = true)
    private DoctorEntity doctorEntity;




}
