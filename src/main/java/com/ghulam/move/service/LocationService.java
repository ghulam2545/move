package com.ghulam.move.service;

import com.ghulam.move.dtos.DriverLocationRequest;
import com.ghulam.move.dtos.NearByDriverRequest;
import com.ghulam.move.dtos.NearByDriverResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.ghulam.move.constant.Constants.DRIVERS_REDIS_KEY;
import static com.ghulam.move.constant.Constants.MAX_NEARBY_DRIVERS_COUNT;

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

    public Set<NearByDriverResponse> getNearbyDrivers(NearByDriverRequest nearByDriverRequest) {
        log.info("Finding nearby drivers for location: ({}, {}) with radius: {}",
                nearByDriverRequest.longitude(), nearByDriverRequest.latitude(), nearByDriverRequest.radius());

        Circle circle = new Circle(
                new Point(nearByDriverRequest.longitude(), nearByDriverRequest.latitude()),
                new Distance(nearByDriverRequest.radius(), Metrics.KILOMETERS)
        );

        var geoResults = redisTemplate.opsForGeo().radius(DRIVERS_REDIS_KEY, circle,
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                        .includeCoordinates()
                        .includeDistance()
                        .sortAscending()
                        .limit(MAX_NEARBY_DRIVERS_COUNT)
        );

        Set<NearByDriverResponse> nearByDrivers = new HashSet<>();
        if (geoResults == null) {
            geoResults.forEach(result -> {
                RedisGeoCommands.GeoLocation<String> driver = result.getContent();
                nearByDrivers.add(new NearByDriverResponse(
                        driver.getName(),
                        driver.getPoint().getX(),
                        driver.getPoint().getY(),
                        result.getDistance().getValue()
                ));
            });
        } else {
            log.info("No nearby drivers found for the given location and radius.");
        }

        return nearByDrivers;
    }

}
