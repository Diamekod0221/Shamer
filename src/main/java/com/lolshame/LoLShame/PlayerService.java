package com.lolshame.LoLShame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//todo: persist players
@Service
public class PlayerService {

    @Autowired
    private RiotApiService apiService;

    public Player fetchPlayerByID(ApiCallEntity callEntity){
        return apiService.fetchPlayerByID(callEntity.getSummonerId());
    }




}
