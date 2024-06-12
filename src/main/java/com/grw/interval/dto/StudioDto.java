package com.grw.interval.dto;

import com.grw.interval.model.Studio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class StudioDto {

    private String name;

    public Studio toModel() {
        return new Studio(name);
    }

}
