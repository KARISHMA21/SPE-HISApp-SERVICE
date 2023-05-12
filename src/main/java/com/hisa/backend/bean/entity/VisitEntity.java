package com.hisa.backend.bean.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
//<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data //for getters and setters
@AllArgsConstructor
@NoArgsConstructor
@Table(name="visit")
public class VisitEntity implements Serializable {

    public VisitEntity_Doctor_Patient_Comp_key getDid_pid() {
        return did_pid;
    }

    public void setDid_pid(VisitEntity_Doctor_Patient_Comp_key did_pid) {
        this.did_pid = did_pid;
    }


    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @EmbeddedId
    private VisitEntity_Doctor_Patient_Comp_key did_pid;

    private Date lastVisited;

    public void setLastVisited(Date lastVisited) {
        this.lastVisited = lastVisited;
    }

    private String patientName;
}
