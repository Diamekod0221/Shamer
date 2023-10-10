package com.lolshame.LoLShame;

import com.lolshame.LoLShame.player.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.util.List;

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

    @Autowired
    public RiotApiService(RestTemplateBuilder builder){
        this.restTemplate = builder.build();
    }

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

        return matchesByPuuidApiURL +
                puuid +
                "/ids?" +
                "type=ranked" +
                "&startTime=" +
                startTimestamp() +
                "&start=0" +
                "&count=" +
                matchCount +
                "&api_key=" +
                apiKey;
    }


    private long startTimestamp(){
        LocalDateTime matchlistStart = LocalDateTime.of(2021, Month.JUNE, 16, 0, 0);
        Instant matchlistStartInstant = matchlistStart.toInstant(ZoneOffset.UTC);

        Instant sevenDaysAgo = Instant.now().minus(Duration.ofDays(7));

        return Duration.between(matchlistStartInstant, sevenDaysAgo).toSeconds();
    }


    public String fetchMatchData(String matchId) {
        HttpEntity<String> requestEntity = this.setUpRequestEntity();

        return restTemplate.exchange(matchIDUrl(matchId), HttpMethod.GET, requestEntity, String.class).getBody();
    }

    private String matchIDUrl(String matchId){
        return matchesByIDApiURL
                + matchId +
                "?api_key=" +
                apiKey;
    }

    //some weird shit for riot api request formatting
    private HttpEntity<String> setUpRequestEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","*/*");
        return new HttpEntity<>(headers);
    }



}
