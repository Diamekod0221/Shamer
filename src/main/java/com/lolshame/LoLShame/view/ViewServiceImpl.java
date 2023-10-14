package com.lolshame.LoLShame.view;

import com.lolshame.LoLShame.player.results.PlayerResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.View;

@Slf4j
@Service
public class ViewServiceImpl implements ViewService {

    @Value("${view.image.shame}")
    private String shamingImagePath;

    @Value("${view.image.cleen}")
    private String cleenImagePath;

    @Value("${view.message.shame}")
    private String shamingMessage;

    @Value("${view.message.cleen}")
    private String cleenMessage;

    public String getResultsPage(Model model, PlayerResults playerResults) {

        String imageUrl = chooseImageToReturn(playerResults);
        String customString = chooseMessage(playerResults);

        model.addAttribute("imageUrl", imageUrl);
        model.addAttribute("customString", customString);

        model.addAttribute("playerResults", PlayerResults.booleanizeResults(playerResults));
        return "player-results-template";
    }

    @Override
    public void addDataSource(Model model, String dataSource) {
        model.addAttribute("dataSource", dataSource);
    }

    public String chooseImageToReturn(PlayerResults playerResults){
        return (playerResults.areShamingResults() ? shamingImagePath : cleenImagePath);
    }

    public String chooseMessage(PlayerResults playerResults){
        return (playerResults.areShamingResults() ? shamingMessage : cleenMessage);
    }

}
