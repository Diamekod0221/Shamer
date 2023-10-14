package com.lolshame.LoLShame.player.results;

import com.lolshame.LoLShame.match.Match;
import com.lolshame.LoLShame.player.Player;

import java.util.List;

public interface PlayerResultsService {

     PlayerResults providePlayerResults(List<Match> matchList, Player player);


     PlayerResults providePlayerResults(Match match, Player player);
}
