package com.lolshame.LoLShame;

import com.lolshame.LoLShame.player.Player;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public interface RiotApiService {
    Player fetchPlayerByID(String summonerId);

    List<String> fetchMatchIds(String puuid);

    String fetchMatchData(String matchId) throws HttpClientErrorException;

}
