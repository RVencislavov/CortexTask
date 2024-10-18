package com.example.CortexTask.service.serviceImpl;

import com.example.CortexTask.exception.ExcursionNotFoundException;
import com.example.CortexTask.mapper.ExcursionMapper;
import com.example.CortexTask.persistence.dto.ExcursionDTO;
import com.example.CortexTask.persistence.entity.Excursion;
import com.example.CortexTask.repository.ExcursionRepository;
import com.example.CortexTask.service.ExcursionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExcursionServiceImpl implements ExcursionService {

    @Autowired
    private ExcursionRepository excursionRepository;

    @Autowired
    private ExcursionMapper excursionMapper;

    public List<ExcursionDTO> getAllExcursions() {
        return excursionRepository.findAll()
                .stream()
                .map(excursionMapper::toDto)
                .toList();
    }

    public ExcursionDTO getExcursionById(Long id) {
        Optional<Excursion> excursion = excursionRepository.findById(id);
        if (excursion.isEmpty()) {
            throw new ExcursionNotFoundException("Excursion not found with ID: " + id);
        }
        return excursionMapper.toDto(excursion.get());
    }


    public ExcursionDTO createExcursion(ExcursionDTO excursionDTO) {
        Excursion excursion = excursionMapper.toEntity(excursionDTO);
        Excursion savedExcursion = excursionRepository.save(excursion);
        return excursionMapper.toDto(savedExcursion);
    }

    public ExcursionDTO updateExcursion(Long id, ExcursionDTO excursionDTO) {
        Optional<Excursion> existingExcursion = excursionRepository.findById(id);
        if (existingExcursion.isEmpty()) {
            throw new ExcursionNotFoundException("Excursion not found with ID: " + id);
        }
        Excursion excursion = excursionMapper.toEntity(excursionDTO);
        excursion.setId(id);
        Excursion updatedExcursion = excursionRepository.save(excursion);
        return excursionMapper.toDto(updatedExcursion);
    }

    public void deleteExcursion(Long id) {
        Optional<Excursion> excursion = excursionRepository.findById(id);
        if (excursion.isEmpty()) {
            throw new ExcursionNotFoundException("Excursion not found with ID: " + id);
        }
        excursionRepository.deleteById(id);
    }
}
