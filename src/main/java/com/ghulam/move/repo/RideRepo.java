package com.ghulam.move.repo;

import com.ghulam.move.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RideRepo extends JpaRepository<Ride, String> {

    Set<Ride> findByCustomerIdOrderByCreatedTimestampDesc(String customerId);
}
