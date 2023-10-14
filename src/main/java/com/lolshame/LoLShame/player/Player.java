package com.lolshame.LoLShame.player;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private String id;
    private String accountId;
    private String puuid;
    private String name;
    private String profileIconId;
    private Long revisionDate;
    private Long summonerLevel;

}
