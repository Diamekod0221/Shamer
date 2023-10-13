package com.lolshame.LoLShame.controller;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiCallRepository extends JpaRepository<ApiCallEntity, Long> {
}
