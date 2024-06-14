package com.grw.interval.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AwardIntervalsDto {

    List<ProducerIntervalDto> min;

    List<ProducerIntervalDto> max;
}
