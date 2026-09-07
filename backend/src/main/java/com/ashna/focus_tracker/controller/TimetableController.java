package com.ashna.focus_tracker.controller;

import com.ashna.focus_tracker.dto.response.GenerateTimetableResponse;
import com.ashna.focus_tracker.scheduling.TimetableGeneratorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/timetable")
public class TimetableController {

    private final TimetableGeneratorService generatorService;

    public TimetableController(TimetableGeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @PostMapping("/generate")
    public GenerateTimetableResponse generate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam int days) {
        return generatorService.generate(startDate, days);
    }
}