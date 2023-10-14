package com.lolshame.LoLShame.controller;

import com.lolshame.LoLShame.caching.ApiCallEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiCallRepository extends JpaRepository<ApiCallEntity, Long> {
}
