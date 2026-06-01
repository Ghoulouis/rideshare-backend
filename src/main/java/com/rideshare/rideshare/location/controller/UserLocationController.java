package com.rideshare.rideshare.location.controller;

import com.rideshare.rideshare.location.dto.UpdateLocationRequest;
import com.rideshare.rideshare.location.service.UserLocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class UserLocationController {

    private final UserLocationService userLocationService;

    @PostMapping("/me")
    public ResponseEntity<Void> updateMyLocation(
            Authentication authentication,
            @Valid @RequestBody UpdateLocationRequest request
    ) {
        Long userId = Long.valueOf(authentication.getName());

        userLocationService.updateMyLocation(userId, request);

        return ResponseEntity.noContent().build();
    }
}