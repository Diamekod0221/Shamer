package com.lolshame.LoLShame.caching;

import com.lolshame.LoLShame.controller.NewApiCall;
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
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiCallEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;

    private String summonerId;

    @CreatedDate
    private LocalDateTime timestamp;

    @Autowired
    public ApiCallEntity(NewApiCall summonerId) throws UnsupportedEncodingException {
        this.summonerId = URLDecoder.decode(summonerId.summonerId(), StandardCharsets.UTF_8);
    }


}
