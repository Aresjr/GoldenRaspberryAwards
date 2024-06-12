package com.grw.interval.dto;

import com.grw.interval.model.Producer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ProducerDto {

    private String name;

    public Producer toModel() {
        return new Producer(name);
    }

}
