package com.lolshame.LoLShame.controller;

public record NewApiCall(String summonerId) {

    public static NewApiCall of(String summonerId){
        validate(summonerId);
        return new NewApiCall(summonerId);
    }

    public NewApiCall(String summonerId){
        this.summonerId = summonerId;
    }

    public static void validate(String summonerId) throws IllegalArgumentException{
        if(summonerId.isBlank() || summonerId.length() > 15){
            throw new IllegalArgumentException("Not a valid summoner name: " + summonerId);
        }
    }


}
