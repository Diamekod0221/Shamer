package com.lolshame.LoLShame.caching;

import com.lolshame.LoLShame.controller.ApiCallEntity;
import com.lolshame.LoLShame.player.results.PlayerResults;

import java.util.List;

public interface CacheService {

    boolean checkIfCanFetch(ApiCallEntity apiCall);

    PlayerResults fetchSummonerFromDB(ApiCallEntity apiCall);

    void saveResults(PlayerResults results);

    List<PlayerResults> fetchResultsList();

    void deleteResultsById(String resultsId);
}
