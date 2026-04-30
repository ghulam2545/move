package com.ghulam.move.repo;

import com.ghulam.move.entity.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditRepo extends JpaRepository<Audit, String> {

    Optional<Audit> findByTraceId(String traceId);
    Page<Audit> findByUserId(String userId, Pageable pageable);
    Page<Audit> findByServiceName(String name, Pageable pageable);
}
