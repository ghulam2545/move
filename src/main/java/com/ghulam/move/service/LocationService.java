package com.ghulam.move.service;

import com.ghulam.move.dtos.DriverLocationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.ghulam.move.constant.Constants.DRIVERS_REDIS_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    private final RedisTemplate<String, String> redisTemplate;

    public void updateDriverLocation(DriverLocationRequest driverLocationRequest) {
        log.info("Updating location for driver: {}", driverLocationRequest.driverId());

        Point point = new Point(driverLocationRequest.longitude(), driverLocationRequest.latitude());
        redisTemplate.opsForGeo().add(DRIVERS_REDIS_KEY, point, driverLocationRequest.driverId());
    }
}
