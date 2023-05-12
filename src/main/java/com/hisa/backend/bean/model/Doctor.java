package com.hisa.backend.bean.model;

public class Doctor {
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

    public String getSuper_did() {
        return super_did;
    }

    public void setSuper_did(String super_did) {
        this.super_did = super_did;
    }

    private String did;
    private String dname;
    private String specialization;
    private String super_did;

}
