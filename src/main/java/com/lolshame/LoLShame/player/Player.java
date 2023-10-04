package com.lolshame.LoLShame.player;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DBid;
    private String id;
    private String accountId;
    private String puuid;
    private String name;
    private String profileIconId;
    private Long revisionDate;
    private Long summonerLevel;

}
