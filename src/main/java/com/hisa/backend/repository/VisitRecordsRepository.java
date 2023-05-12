package com.hisa.backend.repository;

import com.hisa.backend.bean.entity.VisitEntity;
import com.hisa.backend.bean.entity.VisitEntity_Doctor_Patient_Comp_key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
@Repository
public interface VisitRecordsRepository  extends CrudRepository<VisitEntity,VisitEntity_Doctor_Patient_Comp_key> {

    @Query("select t from VisitEntity  t  where t.did_pid.did =:did")
    public List<VisitEntity> getRecords(@Param("did") String did);

    @Modifying
    @Query("UPDATE VisitEntity  t SET " +
            "t.patientName=:name "+
            "WHERE " +
            "t.did_pid.pid=:pid")
    public void processprofileUpdates(@Param("pid") String pid,@Param("name") String name);
    @Query("select count(t.did_pid.pid) from VisitEntity  t  where t.did_pid.did =:did")
    public BigInteger getPatientCount(@Param("did") String did);


}
