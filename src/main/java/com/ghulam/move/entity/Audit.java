package com.ghulam.move.entity;

import com.ghulam.move.utils.UUIDGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "audit_logs",
        indexes = {
                @Index(name = "idx_audit_trace_id", columnList = "trace_id"),
                @Index(name = "idx_audit_user_id", columnList = "user_id"),
                @Index(name = "idx_audit_service", columnList = "service_name")
        }
)
@NoArgsConstructor
@AllArgsConstructor
public class Audit {

    @Id
    @UUIDGenerator
    private String id;
    private String traceId;
    private String userId;
    private String serviceName;
    @Column(name = "request", columnDefinition = "jsonb")
    private String request;
    @Column(name = "response", columnDefinition = "jsonb")
    private String response;
    @Column(name = "headers", columnDefinition = "jsonb")
    private String headers;
    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;
    @CreationTimestamp
    private LocalDateTime createdTimestamp;
}
