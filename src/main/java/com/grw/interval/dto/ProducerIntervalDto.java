package com.grw.interval.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ProducerIntervalDto {

    String producer;

    Integer interval;

    Integer previousWin;

    Integer followingWin;

    public static ProducerIntervalDto fromQueryObject(Object[] object) {
        return new ProducerIntervalDto((String) object[0],
                (Integer) object[1], (Integer) object[2], (Integer) object[3]);
    }
}
