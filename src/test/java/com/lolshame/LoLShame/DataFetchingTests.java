package com.lolshame.LoLShame;

import com.lolshame.LoLShame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.DefaultResponseCreator;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DataFetchingTests {

    @Autowired
    private RestTemplateBuilder builder;

    private RiotApiService riotApiService;

    private MockRestServiceServer mockServer;


    @BeforeEach
    public void basicSetUp() {
        builder = new RestTemplateBuilder();
        riotApiService = new RiotApiService(builder);

        ReflectionTestUtils.setField(riotApiService, "apiKey", "RGAPI-466e9559-14ca-4d4b-942d-40da8547dac1");
        ReflectionTestUtils.setField(riotApiService, "summonerByNameApiURL", "https://eun1.api.riotgames.com/lol/summoner/v4/summoners/by-name/");
        ReflectionTestUtils.setField(riotApiService, "matchesByPuuidApiURL", "https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/");
        ReflectionTestUtils.setField(riotApiService, "matchesByIDApiURL", "https://europe.api.riotgames.com/lol/match/v5/matches/");

        mockServer = MockRestServiceServer.createServer(builder.build());
    }

    @Test
    public void testGetPlayerByIdMock() throws IOException {
        String playerId = "Diamekod";
        String apiUrl = "https://eun1.api.riotgames.com/lol/summoner/v4/summoners/by-name/Diamekod?api_key=RGAPI-a6076e22-425b-48f5-8fd3-99f9665ba723";

        // Prepare a mock response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ClassPathResource jsonResource = new ClassPathResource("testing-json.json");
        byte[] jsonBytes = jsonResource.getInputStream().readAllBytes();

        DefaultResponseCreator responseCreator = MockRestResponseCreators
                .withSuccess(jsonBytes, MediaType.APPLICATION_JSON)
                .headers(headers);

        mockServer.expect(requestTo(apiUrl))
                .andRespond(responseCreator);

        // Test the service method
        Player responseEntity = riotApiService.fetchPlayerByID(playerId);

        assertEquals(playerId, responseEntity.getName());
    }

    @Test
    public void playerFetchRealApi(){
        String expectedPuuid = "LrzhRzwlynzi77bimwDBjBmhyBN4jajWOBN38Sw2HKEBNMtesEa_5gtg9NCTcbpgSyJRg4d5NYShRA";
        String playerId = "Diamekod";
        Player responseEntity = riotApiService.fetchPlayerByID(playerId);

        System.out.println(responseEntity.getPuuid());
        assertEquals(expectedPuuid, responseEntity.getPuuid());
    }

    @Test
    public void matchListFetchRealApi(){
        String puuid = "LrzhRzwlynzi77bimwDBjBmhyBN4jajWOBN38Sw2HKEBNMtesEa_5gtg9NCTcbpgSyJRg4d5NYShRA";

        String expectedURL = "https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/LrzhRzwlynzi77bimwDBjBmhyBN4jajWOBN38Sw2HKEBNMtesEa_5gtg9NCTcbpgSyJRg4d5NYShRA/ids?startTime=71697725&start=0&count=20&api_key=RGAPI-a6076e22-425b-48f5-8fd3-99f9665ba723";


        System.out.println(riotApiService.fetchMatchIds(puuid));

        //assertEquals(expectedURL, actual);
    }

}



