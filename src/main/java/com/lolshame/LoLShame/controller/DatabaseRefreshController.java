package com.lolshame.LoLShame.controller;

import com.lolshame.LoLShame.player.results.ResultsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class DatabaseRefreshController {

    private final ResultsRepository resultsRepository;

    @Autowired
    public DatabaseRefreshController(ResultsRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }

    @GetMapping("/refresh-db")
    public String refreshDatabase() {
        resultsRepository.deleteAll(); // Delete all data in the player-results table
        return "Database refreshed!";
    }
}

