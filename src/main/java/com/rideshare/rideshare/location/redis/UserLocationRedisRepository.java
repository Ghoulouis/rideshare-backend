package com.rideshare.rideshare.location.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class UserLocationRedisRepository {

    private static final String USER_LOCATION_GEO_KEY = "users:locations:geo";
    private static final String USER_LOCATION_KEY_PREFIX = "user:location:";

    private final StringRedisTemplate redisTemplate;

    public void saveUserLocation(Long userId, Double latitude, Double longitude) {
        String userIdStr = userId.toString();

        redisTemplate.opsForGeo().add(
                USER_LOCATION_GEO_KEY,
                new Point(longitude, latitude),
                userIdStr
        );

        String locationKey = USER_LOCATION_KEY_PREFIX + userId;

        redisTemplate.opsForHash().put(locationKey, "latitude", latitude.toString());
        redisTemplate.opsForHash().put(locationKey, "longitude", longitude.toString());
        redisTemplate.opsForHash().put(locationKey, "updatedAt", String.valueOf(System.currentTimeMillis()));

        redisTemplate.expire(locationKey, Duration.ofMinutes(10));
    }
}