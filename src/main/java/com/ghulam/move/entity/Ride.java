package com.ghulam.move.entity;

import com.ghulam.move.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "rides")
@NoArgsConstructor
@AllArgsConstructor
public final class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String customerId;
    private String driverId;
    private double pickupLongitude;
    private double pickupLatitude;
    private String pickupAddress;
    private double dropLongitude;
    private double dropLatitude;
    private String dropAddress;
    @Enumerated(EnumType.STRING)
    private RideStatus status;
    private double fare;
    @CreationTimestamp
    private LocalDateTime createdTimestamp;
    @UpdateTimestamp
    private LocalDateTime updatedTimestamp;
    private LocalDateTime rideStartedTimestamp;
    private LocalDateTime rideCompletedTimestamp;
}
