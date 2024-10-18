package com.example.CortexTask.service;

import com.example.CortexTask.persistence.dto.ExcursionDTO;

import java.util.List;

public interface ExcursionService {
    public List<ExcursionDTO> getAllExcursions();
    public ExcursionDTO getExcursionById(Long id);
    public ExcursionDTO createExcursion(ExcursionDTO excursionDTO);
    public ExcursionDTO updateExcursion(Long id, ExcursionDTO excursionDTO);
    public void deleteExcursion(Long id);
}
