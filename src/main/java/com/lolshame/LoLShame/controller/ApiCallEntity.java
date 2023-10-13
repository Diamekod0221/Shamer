package com.lolshame.LoLShame.controller;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;


@Entity
@EntityListeners(ApiCallEntityListener.class)
@Table(name = "calls")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiCallEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String summonerId;

    @CreatedDate
    private LocalDateTime timestamp;

    @Autowired
    public ApiCallEntity(NewApiCall summonerId) throws UnsupportedEncodingException {
        this.summonerId = URLDecoder.decode(summonerId.summonerId(), StandardCharsets.UTF_8);
    }


}
