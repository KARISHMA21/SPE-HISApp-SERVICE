package com.hisa.backend.repository;

import com.hisa.backend.bean.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorEntityRepository extends JpaRepository<DoctorEntity,String> {

    DoctorEntity getByDid(String Did);
}
