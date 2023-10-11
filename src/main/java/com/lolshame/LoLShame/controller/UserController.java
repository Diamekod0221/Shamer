package com.lolshame.LoLShame.controller;

import com.lolshame.LoLShame.match.MatchResponse;
import com.lolshame.LoLShame.player.PlayerService;
import com.lolshame.LoLShame.player.results.PlayerResults;
import com.lolshame.LoLShame.player.results.PlayerResultsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/user-api")

public class UserController {


    @Autowired
    private final PlayerService playerService;

    @GetMapping(path = "/get-summoner/{summonerId}")
    public String processRiotApiCallFront(@PathVariable String summonerId, Model model) throws HttpClientErrorException, IllegalArgumentException{
        NewApiCall configuredInput = NewApiCall.of(summonerId);
        boolean isSaved = checkIfSaved(configuredInput);

        if (isSaved) {
            fetchSummonerFromDB(configuredInput);
            return "results";
        } else {
            PlayerResults playerResults = fetchFromApi(configuredInput);

            model.addAttribute("playerResults", playerResults);
            return "player-results-template";
        }
    }


    @ExceptionHandler(HttpClientErrorException.class)
    public String dataFetchingError(){
        return "fetching-error";
    }


    @ExceptionHandler(ResponseStatusException.class)
    public String invalidSummonerIdError(){return "bad-request";}


    private boolean checkIfSaved(NewApiCall configuredInput) {
        //todo: write select and check from db
        return false;
    }

    private PlayerResults fetchSummonerFromDB(NewApiCall configuredInput) {
        //todo: write db fetcher
        return null;

    }


    private PlayerResults fetchFromApi(NewApiCall configuredInput) throws HttpClientErrorException, InternalError {
        ApiCallEntity callEntity = new ApiCallEntity(configuredInput);
        return playerService.makeApiRequest(callEntity);
    }

    @ExceptionHandler(InternalError.class)
    public String noPlayerResultsError(){return "no-results-error";}

}















