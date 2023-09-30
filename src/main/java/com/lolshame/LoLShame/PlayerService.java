package com.lolshame.LoLShame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


//todo: persist players
@Service
public class PlayerService {

    @Autowired
    private MatchService matchService;

    @Autowired
    private RiotApiService apiService;

    public MatchResponse makeApiRequest(ApiCallEntity apiCall){
        return this.produceShamingResponse(getMatchList(getPlayerPuuid(apiCall)));

    }

    private String getPlayerPuuid(ApiCallEntity callEntity){
        return this.fetchPlayerByID(callEntity).getPuuid();
    }

    private List<Match> getMatchList(String puuid){
        return matchService.getMatchList(puuid);
    }

    private MatchResponse produceShamingResponse(List<Match> matchList){
        return new MatchResponse(matchList);
    }

    public Player fetchPlayerByID(ApiCallEntity callEntity){
        return apiService.fetchPlayerByID(callEntity.getSummonerId());
    }




}
