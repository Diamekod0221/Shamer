package com.lolshame.LoLShame.player;

import com.lolshame.LoLShame.RiotApiService;
import com.lolshame.LoLShame.caching.ApiCallEntity;
import com.lolshame.LoLShame.match.Match;
import com.lolshame.LoLShame.match.MatchService;
import com.lolshame.LoLShame.player.results.PlayerResults;
import com.lolshame.LoLShame.player.results.PlayerResultsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements  PlayerService{

    private MatchService matchService;

    private RiotApiService apiService;

    private PlayerResultsService playerResultsServiceImpl;

    public PlayerResults makeApiRequest(ApiCallEntity apiCall) throws HttpClientErrorException {
        Player player = fetchPlayerByID(apiCall);

        List<Match> matchList = this.getMatchList(player.getPuuid());
        if(!matchList.isEmpty()) {
            return playerResultsServiceImpl.providePlayerResults(matchList, player);
        }
        else{
            throw new InternalError();
        }
    }

    public List<Match> getMatchList(String puuid) throws HttpClientErrorException {
        return matchService.getMatchList(puuid);
    }


    public Player fetchPlayerByID(ApiCallEntity callEntity){
        return apiService.fetchPlayerByID(callEntity.getSummonerId());
    }











}
