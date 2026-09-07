package com.ashna.focus_tracker.service;

import com.ashna.focus_tracker.dto.request.AvailabilityRequest;
import com.ashna.focus_tracker.dto.response.AvailabilityResponse;
import com.ashna.focus_tracker.entity.UserAvailability;
import com.ashna.focus_tracker.repository.UserAvailabilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAvailabilityService {

    private final UserAvailabilityRepository repository;

    public UserAvailabilityService(UserAvailabilityRepository repository) {
        this.repository = repository;
    }

    public List<AvailabilityResponse> getAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AvailabilityResponse add(AvailabilityRequest request) {
        UserAvailability entity = new UserAvailability();
        entity.setDayOfWeek(request.getDayOfWeek());
        entity.setStartTime(request.getStartTime());
        entity.setEndTime(request.getEndTime());
        entity.setLabel(request.getLabel());
        entity.setExcluded(request.isExcluded());
        return toResponse(repository.save(entity));
    }

    private AvailabilityResponse toResponse(UserAvailability entity) {
        AvailabilityResponse response = new AvailabilityResponse();
        response.setId(entity.getId());
        response.setDayOfWeek(entity.getDayOfWeek());
        response.setStartTime(entity.getStartTime());
        response.setEndTime(entity.getEndTime());
        response.setLabel(entity.getLabel());
        response.setExcluded(entity.isExcluded());
        return response;
    }
}