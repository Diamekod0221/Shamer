package com.lolshame.LoLShame;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api-call-endpoint")

public class UserController {


    @Autowired
    private final MatchRepository matchRepository;
    @Autowired
    private final PlayerRepository playerRepository;
    @Autowired
    private final RiotApiService apiService;


    @GetMapping(path = "/get-summoner/{summonerId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MatchResponse> processRiotApiCall(@PathVariable String summonerId) throws IOException {
        NewApiCall configuredInput = configureInput(summonerId);
        boolean isSaved = checkIfSaved(configuredInput);
        if (isSaved) {
            return ResponseEntity.ok(fetchSummonerFromDB(configuredInput));
        } else {
            return fetchFromApi(configuredInput);
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
    }

    private MatchResponse fetchSummonerFromDB(NewApiCall configuredInput) {
        //todo: write db fetcher

    }

    private ResponseEntity<MatchResponse> fetchFromApi(NewApiCall configuredInput) {
        ApiCallEntity callEntity = new ApiCallEntity(configuredInput);
        List<Match> matchList = apiService.makeApiRequest(callEntity);
        MatchProcessing processor = new MatchProcessing();

        List<MatchResponse> response = processor.extractMatchDetails(matchList);

        return ResponseEntity.ok(response);
    }


    private String
}














}
