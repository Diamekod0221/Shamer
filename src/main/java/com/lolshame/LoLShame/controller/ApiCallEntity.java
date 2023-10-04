package com.lolshame.LoLShame.controller;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Entity
@EntityListeners(ApiCallEntityListener.class)
@Table(name = "calls")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiCallEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String summonerId;

    @CreatedDate
    private LocalDateTime timestamp;

    @Autowired
    public ApiCallEntity(NewApiCall summonerId){
        this.summonerId = summonerId.summonerId();
    }


}