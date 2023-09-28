package com.lolshame.LoLShame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Autowired
    private RiotApiService apiService;


    public List<Match> getMatchList(String puuid){
        return fetchMatches(getMatchIdsList(puuid));
    }

    private List<String> getMatchIdsList(String puuid){
        return apiService.fetchMatchIds(puuid);
    }

    private List<Match> fetchMatches(List<String> matchIds){
        return matchIds.stream()
                .map(apiService::fetchMatchData)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }



}
