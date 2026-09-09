package com.ashna.focus_tracker.controller;

import com.ashna.focus_tracker.dto.request.LinkRequest;
import com.ashna.focus_tracker.dto.response.LinkResponse;
import com.ashna.focus_tracker.service.LinkService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/links")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public List<LinkResponse> getAll() {
        return linkService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LinkResponse create(@RequestBody LinkRequest request) {
        return linkService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        linkService.delete(id);
    }
}