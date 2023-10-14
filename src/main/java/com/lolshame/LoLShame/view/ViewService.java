package com.lolshame.LoLShame.view;

import com.lolshame.LoLShame.player.results.PlayerResults;
import org.springframework.ui.Model;

public interface ViewService {
    String getResultsPage(Model model, PlayerResults playerResults);

    void addDataSource(Model model, String dataSource);
}
