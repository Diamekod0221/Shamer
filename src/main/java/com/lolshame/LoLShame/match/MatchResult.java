package com.lolshame.LoLShame.match;

import com.lolshame.LoLShame.player.Player;
import jakarta.persistence.*;

@Entity
public class MatchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Player player;
    @OneToOne
    private Match match;
    private boolean win;


}
