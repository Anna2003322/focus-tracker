package com.ashna.focus_tracker.service;

import com.ashna.focus_tracker.dto.request.LinkRequest;
import com.ashna.focus_tracker.dto.response.LinkResponse;
import com.ashna.focus_tracker.entity.Goal;
import com.ashna.focus_tracker.entity.Link;
import com.ashna.focus_tracker.exception.ResourceNotFoundException;
import com.ashna.focus_tracker.repository.GoalRepository;
import com.ashna.focus_tracker.repository.LinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final GoalRepository goalRepository;

    public LinkService(LinkRepository linkRepository, GoalRepository goalRepository) {
        this.linkRepository = linkRepository;
        this.goalRepository = goalRepository;
    }

    public List<LinkResponse> getAll() {
        return linkRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public LinkResponse create(LinkRequest request) {
        Link link = new Link();
        link.setTitle(request.getTitle());
        link.setUrl(request.getUrl());
        link.setNotes(request.getNotes());
        if (request.getGoalId() != null) {
            Goal goal = goalRepository.findById(request.getGoalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Goal not found with id " + request.getGoalId()));
            link.setGoal(goal);
        }
        return toResponse(linkRepository.save(link));
    }

    public void delete(Long id) {
        if (!linkRepository.existsById(id)) {
            throw new ResourceNotFoundException("Link not found with id " + id);
        }
        linkRepository.deleteById(id);
    }

    private LinkResponse toResponse(Link link) {
        LinkResponse r = new LinkResponse();
        r.setId(link.getId());
        r.setTitle(link.getTitle());
        r.setUrl(link.getUrl());
        r.setNotes(link.getNotes());
        if (link.getGoal() != null) r.setGoalId(link.getGoal().getId());
        return r;
    }
}