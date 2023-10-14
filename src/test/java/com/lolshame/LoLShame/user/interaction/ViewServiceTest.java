package com.lolshame.LoLShame.user.interaction;

import com.lolshame.LoLShame.view.ViewServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@ConfigurationProperties(value = "application-test.properties")
public class ViewServiceTest {

    @Value("${view.image.shame}")
    private String shamingImagePath;

    @Value("${view.image.cleen}")
    private String cleenImagePath;

    @Value("${view.message.shame}")
    private String shamingMessage;

    @Value("${view.message.cleen}")
    private String cleenMessage;

    @Autowired
    private ViewServiceImpl viewService;
    @Test
    public void choosesShamingImageProperly(){

        assertEquals("wrong image path found", shamingImagePath, viewService.chooseImageToReturn(PlayerResultsTests.shamingResults));
    }

    @Test
    public void choosesCleanImageProperly(){

        assertEquals("wrong image path found", cleenImagePath, viewService.chooseImageToReturn(PlayerResultsTests.cleanResults));
    }

    @Test
    public void choosesShamingVerdictProperly(){

        assertEquals("wrong verdict path found", shamingMessage, viewService.chooseMessage(PlayerResultsTests.shamingResults));
    }

    @Test
    public void choosesCleanVerdictProperly(){

        assertEquals("wrong verdict path found", cleenMessage, viewService.chooseMessage(PlayerResultsTests.cleanResults));
    }

}
