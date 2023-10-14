package com.lolshame.LoLShame.controller;

import com.lolshame.LoLShame.caching.ApiCallEntity;
import com.lolshame.LoLShame.caching.CacheService;
import com.lolshame.LoLShame.player.PlayerService;
import com.lolshame.LoLShame.player.results.PlayerResults;
import com.lolshame.LoLShame.view.ViewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/shamer")
public class UserController {


    private final PlayerService playerService;

    private final ViewService viewService;

    private final CacheService cacheService;

    private final ApiCallRepository callRepository;

    @GetMapping(path = "/")
    public String serveWelcomePage(){
        return "welcome";
    }

    @GetMapping(path = "/get-summoner")
    public String serveHelpPage(HttpServletRequest request){
        return "help";
    }

    @GetMapping(path = "/get-summoner/{summonerId:.+}")
    public String processRiotApiCallFront(@PathVariable String summonerId, Model model)
            throws HttpClientErrorException, IllegalArgumentException, UnsupportedEncodingException {
        NewApiCall configuredInput = NewApiCall.of(summonerId);
        ApiCallEntity callEntity = new ApiCallEntity(configuredInput);

        callRepository.save(callEntity);

        PlayerResults playerResults;
        if(cacheService.checkIfCanFetch(callEntity)) {
            playerResults = cacheService.fetchSummonerFromDB(callEntity);
            viewService.addDataSource(model, "ShamerDB");
        } else {
            playerResults = playerService.makeApiRequest(callEntity);
            cacheService.saveResults(playerResults, callEntity.getSummonerId());
            viewService.addDataSource(model,"RiotApi");

        }
        return viewService.getResultsPage(model, playerResults);
    }

    @GetMapping(path = "/error")
    public String errorPage(){
        return "bad-request";
    }


    @ExceptionHandler(InternalError.class)
    public String noPlayerResultsError(){return "no-results-error";}


    @ExceptionHandler(HttpClientErrorException.class)
    public String dataFetchingError(){
        return "fetching-error";
    }


    @ExceptionHandler(ResponseStatusException.class)
    public String invalidSummonerIdError(){return "bad-request";}

    @ExceptionHandler(UnsupportedEncodingException.class)
    public String invalidEncodingError(){return "bad-request";}


}















