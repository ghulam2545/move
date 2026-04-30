package com.ghulam.move.kafka.event;

public record AuditLogEvent(
        String traceId,
        String userId,
        String serviceName,
        String request,
        String response,
        String header,
        String metadata
) {
}
