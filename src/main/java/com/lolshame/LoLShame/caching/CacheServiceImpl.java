package com.lolshame.LoLShame.caching;

import com.lolshame.LoLShame.controller.ApiCallEntity;
import com.lolshame.LoLShame.player.results.PlayerResults;
import com.lolshame.LoLShame.player.results.PlayerResultsEntity;
import com.lolshame.LoLShame.player.results.ResultsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class CacheServiceImpl implements CacheService {

    private ResultsRepository repository;

    private Clock clock;

    @Override
    public boolean checkIfCanFetch(ApiCallEntity apiCall) {
        String id = apiCall.getSummonerId();
        return checkIfSaved(id) || checkIfOutdated(id);
    }

    private boolean checkIfSaved(String id){
        return (repository.findById(id).isPresent());
    }

    private boolean checkIfOutdated(String id){
        Optional<PlayerResultsEntity> results = repository.findById(id);
        if(results.isPresent()){
            boolean isOutdated = results.get().getTimestamp().isBefore(LocalDateTime.now(clock).minusDays(7L));
            if(isOutdated){
                repository.deleteById(id);
                return true;
            }
            else{
                return false;
            }
        }
        log.warn("Didn't find record: " + id + " in DB while checking if outdated.");
        return true;
    }

    @Override
    public PlayerResults fetchSummonerFromDB(ApiCallEntity apiCall) {
        Optional<PlayerResultsEntity> fetched = repository.findById(apiCall.getSummonerId());
        return (fetched.map(PlayerResultsEntity::mapToResults).orElse(null));
    }

    @Override
    public void saveResults(PlayerResults results) {
        repository.save(results.mapToEntity());
    }

    @Override
    public List<PlayerResults> fetchResultsList() {
        return repository.findAll().stream().map(PlayerResultsEntity::mapToResults).toList();
    }

    @Override
    public void deleteResultsById(String resultsId) {
        repository.deleteById(resultsId);
    }


}
