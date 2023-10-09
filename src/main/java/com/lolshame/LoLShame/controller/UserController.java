package com.lolshame.LoLShame.controller;

import com.lolshame.LoLShame.match.MatchResponse;
import com.lolshame.LoLShame.player.PlayerService;
import com.lolshame.LoLShame.player.results.PlayerResults;
import com.lolshame.LoLShame.player.results.PlayerResultsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/api-call-endpoint")

public class UserController {


    @Autowired
    private final PlayerService playerService;

    @GetMapping(path = "/get-summoner/{summonerId}")
    public String processRiotApiCallFront(@PathVariable String summonerId, Model model) throws IOException {
        NewApiCall configuredInput = configureInput(summonerId);
        boolean isSaved = checkIfSaved(configuredInput);

        if (isSaved) {
            fetchSummonerFromDB(configuredInput);
            return "results";
        } else {
            PlayerResults playerResults = fetchFromApi(configuredInput);

            model.addAttribute("playerResults", playerResults);
            return "PlayerResultsTemplate";
        }
    }



    private NewApiCall configureInput(String summonerId) {
        try {
            return processInput(summonerId);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid summoner name, can't call. Summoner: "
                    + summonerId + " was requested but not found."
            );
        }
    }

    private NewApiCall processInput(String summonerId) {
        NewApiCall apiCall = new NewApiCall(summonerId);
        NewApiCallValidator.validate(apiCall);
        return apiCall;
    }

    private boolean checkIfSaved(NewApiCall configuredInput) {
        //todo: write select and check from db
        return false;
    }

    private PlayerResults fetchSummonerFromDB(NewApiCall configuredInput) {
        //todo: write db fetcher
        return null;

    }


    private PlayerResults fetchFromApi(NewApiCall configuredInput) {
        ApiCallEntity callEntity = new ApiCallEntity(configuredInput);
        return playerService.makeApiRequest(callEntity);
    }

}















