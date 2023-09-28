package com.lolshame.LoLShame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.*;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.Period.between;

@Slf4j
@Service
public class RiotApiService {

    @Value("${riot.api.key}")
    private String apiKey;

    @Value("${riot.api.summoner.name.url}")
    private String summonerByNameApiURL;

    @Value("${riot.api.match.puuid.url}")
    private String matchesByPuuidApiURL;

    @Value("${riot.api.match.ID.url}")
    private String matchesByIDApiURL;

    private final int matchCount = 30;


    private final RestTemplate restTemplate;

    public RiotApiService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    /*todo: this has to call
   https://eun1.api.riotgames.com/lol/summoner/v4/summoners/by-name/{summoner_name}?api_key=RGAPI-e77791a8-01e1-4e4f-91b1-521851506a44
   filter body for puuid, then call
   https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/
   {puuid}}/ids?start=0&count=20&api_key=RGAPI-e77791a8-01e1-4e4f-91b1-521851506a44
   ,get a list of match ids
   and call
   https://europe.api.riotgames.com/lol/match/v5/matches/{match_id}

   and extract needed data (check what falgs to use)
     */



    public Player fetchPlayerByID(String summonerId){
        return restTemplate.getForObject(summonerApiURL(summonerId), Player.class);
    }

    private String summonerApiURL(String summonerId){
        return summonerByNameApiURL + summonerId + "?api_key=" + apiKey;
    }

    public List<String> fetchMatchIds(String puuid){
        return restTemplate
                .exchange(matchPuuidApiURL(puuid),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<String>>(){}
                )
                .getBody();

    }

    private String matchPuuidApiURL(String puuid){
        //https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/
        //{puuid}}/ids?start=0&count=20&api_key=RGAPI-e77791a8-01e1-4e4f-91b1-521851506a44

        return matchesByPuuidApiURL +
                puuid +
                "/ids?" +
                "start=" +
                startTimestamp() +
                "&count=" +
                matchCount +
                "&api_key=" +
                apiKey;
    }


    private long startTimestamp(){
        LocalDateTime matchlistStart = LocalDateTime.of(2021, Month.JUNE, 16, 0, 0);
        Instant matchlistStartInstant = matchlistStart.toInstant(ZoneOffset.UTC);

        Instant sevenDaysAgo = Instant.now().minus(Duration.ofDays(7));

        return Duration.between(matchlistStartInstant, sevenDaysAgo).toMillis();
    }



    public Optional<Match> fetchMatchData(String matchId) {
        ResponseEntity<String> matchResponse = restTemplate.getForEntity(matchIDUrl(matchId), String.class);
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return Optional.of(mapper.readValue(matchResponse.getBody(), Match.class));
        }
         catch (IOException e){
            log.error("Deserialization failed on matchId: " + matchId + "\n falling back.");
            return Optional.empty();
        }
    }


    //https://europe.api.riotgames.com/lol/match/v5/matches/{match_id}
    private String matchIDUrl(String matchId){
        return matchesByIDApiURL
                + matchId +
                "&api_key=" +
                apiKey;
    }





}
