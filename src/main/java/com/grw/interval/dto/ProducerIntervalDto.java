package com.grw.interval.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ProducerIntervalDto {

    String producerName;

    Integer interval;

    Integer previousWin;

    Integer followingWin;
}
