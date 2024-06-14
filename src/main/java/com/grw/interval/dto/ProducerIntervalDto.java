package com.grw.interval.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ProducerIntervalDto {

    String getProducer();

    @JsonProperty("interval")
    Integer getWinInterval();

    Integer getPreviousWin();

    Integer getFollowingWin();
}
