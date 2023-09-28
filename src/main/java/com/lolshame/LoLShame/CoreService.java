package com.lolshame.LoLShame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoreService {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MatchService matchService;

    public MatchResponse makeApiRequest(ApiCallEntity apiCall){
        return this.produceShamingResponse(getMatchList(getPlayerPuuid(apiCall)));

    }

    private Player getPlayer(ApiCallEntity callEntity){
        return playerService.fetchPlayerByID(callEntity);
    }

    private String getPlayerPuuid(ApiCallEntity callEntity){
        return this.getPlayer(callEntity).getPuuid();
    }

    private List<Match> getMatchList(String puuid){
        return matchService.getMatchList(puuid);
    }

    private MatchResponse produceShamingResponse(List<Match> matchList){
        return new MatchResponse(matchList);
    }

}
