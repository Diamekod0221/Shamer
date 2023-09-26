package com.lolshame.LoLShame;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RiotApiService {

    @Value("${riot.api.key}")
    private String apiKey;

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


    public List<Match> makeApiRequest(ApiCallEntity callEntity) {
        Player fetchedPlayer= fetchPlayer(callEntity);

        List<String> matchIds= fetchMatchIds(fetchedPlayer);

        return fetchMatches(matchIds);
    }


}
