package com.ashna.focus_tracker.controller;

import com.ashna.focus_tracker.dto.request.AvailabilityRequest;
import com.ashna.focus_tracker.dto.response.AvailabilityResponse;
import com.ashna.focus_tracker.service.UserAvailabilityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/availability")
public class UserAvailabilityController {

    private final UserAvailabilityService service;

    public UserAvailabilityController(UserAvailabilityService service) {
        this.service = service;
    }

    @GetMapping
    public List<AvailabilityResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    public AvailabilityResponse add(@RequestBody AvailabilityRequest request) {
        return service.add(request);
    }
}