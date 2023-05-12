package com.hisa.backend.repository;

import com.hisa.backend.bean.entity.EntityCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntityCredentialRepository extends JpaRepository<EntityCredentials,Integer> {
        Optional<EntityCredentials> findByUsername(String username);

//        Optional<EntityCredentials> findByUsername(String username);
}
