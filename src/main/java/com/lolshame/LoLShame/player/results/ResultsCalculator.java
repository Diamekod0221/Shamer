package com.lolshame.LoLShame.player.results;

import com.lolshame.LoLShame.match.Match;

import java.util.List;

@FunctionalInterface
public interface ResultsCalculator {
    PlayerResults getResultsFromList(List<PlayerResults> resultsList);

}
