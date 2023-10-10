package com.lolshame.LoLShame.player;

import com.lolshame.LoLShame.controller.ApiCallEntity;
import com.lolshame.LoLShame.RiotApiService;
import com.lolshame.LoLShame.match.Match;
import com.lolshame.LoLShame.match.MatchResponse;
import com.lolshame.LoLShame.match.MatchService;
import com.lolshame.LoLShame.player.results.PlayerResults;
import com.lolshame.LoLShame.player.results.PlayerResultsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


//todo: persist players

@Service
@AllArgsConstructor
public class PlayerService {

    private MatchService matchService;

    private RiotApiService apiService;

    private PlayerResultsService playerResultsService;

    public PlayerResults makeApiRequest(ApiCallEntity apiCall) {
        Player player = fetchPlayerByID(apiCall);

        List<Match> matchList = this.getMatchList(player.getPuuid());
        return playerResultsService.providePlayerResults(matchList, player);
    }

    public List<Match> getMatchList(String puuid){
        return matchService.getMatchList(puuid);
    }


    public Player fetchPlayerByID(ApiCallEntity callEntity){
        return apiService.fetchPlayerByID(callEntity.getSummonerId());
    }










}
