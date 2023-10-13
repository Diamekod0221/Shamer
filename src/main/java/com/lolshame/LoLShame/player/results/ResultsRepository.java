package com.lolshame.LoLShame.player.results;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultsRepository extends JpaRepository<PlayerResultsEntity, String> {

}
