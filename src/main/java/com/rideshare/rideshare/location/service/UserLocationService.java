package com.rideshare.rideshare.location.service;

import com.rideshare.rideshare.location.dto.UpdateLocationRequest;
import com.rideshare.rideshare.location.redis.UserLocationRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLocationService {

    private final UserLocationRedisRepository userLocationRedisRepository;

    public void updateMyLocation(Long userId, UpdateLocationRequest request) {
        userLocationRedisRepository.saveUserLocation(
                userId,
                request.getLatitude(),
                request.getLongitude()
        );
    }
}