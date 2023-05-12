package com.hisa.backend.repository;

import com.hisa.backend.bean.entity.DoctorEntity;
import com.hisa.backend.bean.entity.MedicalRecordEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MedicalRecordsRepository extends CrudRepository<MedicalRecordEntity,String> {
    @Query("select  t from MedicalRecordEntity  t  where t.pid =:pid")
    public List<MedicalRecordEntity> getRecords(@Param("pid") String pid);


    @Query("select  t from MedicalRecordEntity  t  where t.pid =:pid AND t.rid IN :RecordIds")
    public List<MedicalRecordEntity> getRecordsbyRecordsIds(@Param("RecordIds") List<String> RecordIds,@Param("pid") String pid);

    @Query("select  t from DoctorEntity  t  where t.did =:did")
    public DoctorEntity getDoctorInfo(@Param("did") String did);

}