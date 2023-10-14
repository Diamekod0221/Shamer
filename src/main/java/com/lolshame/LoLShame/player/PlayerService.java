package com.lolshame.LoLShame.player;

import com.lolshame.LoLShame.caching.ApiCallEntity;
import com.lolshame.LoLShame.match.Match;
import com.lolshame.LoLShame.player.results.PlayerResults;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public interface PlayerService {

    PlayerResults makeApiRequest(ApiCallEntity apiCall) throws HttpClientErrorException;

    List<Match> getMatchList(String puuid) throws HttpClientErrorException;

    Player fetchPlayerByID(ApiCallEntity callEntity);
}
