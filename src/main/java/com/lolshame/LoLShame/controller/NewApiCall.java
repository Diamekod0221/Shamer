package com.lolshame.LoLShame.controller;

public record NewApiCall(String summonerId) {


    public void validate(){

        if(summonerId.isBlank() || summonerId.length() > 15){
            throw new IllegalArgumentException("Not a valid summoner name: " + summonerId);
        }
    }
}
