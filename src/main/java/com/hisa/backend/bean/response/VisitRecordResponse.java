package com.hisa.backend.bean.response;
import com.hisa.backend.bean.entity.VisitEntity;

import java.util.List;
public class VisitRecordResponse {


    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getLastVisited() {
        return lastVisited;
    }

    public void setLastVisited(String lastVisited) {
        this.lastVisited = lastVisited;
    }

    private String did;
    private  String pid;
    private  String patientName;
    private  String lastVisited;
    @Override
    public String toString() {
        return "FinalRecords{" +
                "did='" + did + '\'' +
                ", pid='" + pid + '\'' +
                ", patientName='" + patientName + '\'' +
                ", lastVisited='" + lastVisited + '\'' +
                '}';
    }

    public List<VisitEntity> getVisitRecordEntities() {
        return visitRecordEntities;
    }

    public void setVisitRecordEntities(List<VisitEntity> visitRecordEntities) {
        this.visitRecordEntities = visitRecordEntities;
    }

    List<VisitEntity> visitRecordEntities;

}
