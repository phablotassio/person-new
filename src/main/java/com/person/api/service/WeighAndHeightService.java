package com.person.api.service;

import com.person.api.dto.WeightAndHeightDTO;
import com.person.api.mapper.WeighAndHeightMapper;
import com.person.api.model.PersonEntity;
import com.person.api.model.WeighAndHeightEntity;
import com.person.api.repository.WeighAndHeightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WeighAndHeightService {

    private final WeighAndHeightMapper mapper;

    private final WeighAndHeightRepository weighAndHeightRepository;

    public WeighAndHeightService(WeighAndHeightMapper mapper, WeighAndHeightRepository weighAndHeightRepository) {
        this.mapper = mapper;
        this.weighAndHeightRepository = weighAndHeightRepository;
    }

    public List<WeightAndHeightDTO> getWeights() {

        return weighAndHeightRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public WeightAndHeightDTO getWeight(Long personId) {
        return weighAndHeightRepository.getByIdPerson(personId).orElse(null);
    }

    public WeightAndHeightDTO createWeightAnd(WeightAndHeightDTO weightAndHeightDTO, PersonEntity personEntity) {

        if (Objects.isNull(weightAndHeightDTO) || Objects.isNull(weightAndHeightDTO.getHeightDTO()) || Objects.isNull(weightAndHeightDTO.getWeightDTO())) {
            return null;
        }

        final WeighAndHeightEntity weighAndHeightEntity = mapper.toWeighAndHeight(weightAndHeightDTO, personEntity);

        return mapper.toResponse(weighAndHeightRepository.save(weighAndHeightEntity));
    }


}
