package com.grw.interval.service;

import com.grw.interval.dto.StudioDto;
import com.grw.interval.model.Studio;
import com.grw.interval.repository.StudioRepository;
import org.springframework.stereotype.Service;

@Service
public class StudioService {

    private final StudioRepository studioRepository;

    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    public Studio upsertStudio(StudioDto studioDto) {
        return studioRepository.getOneByName(studioDto.getName())
                .orElseGet(() -> studioRepository.save(studioDto.toModel()));
    }

}
