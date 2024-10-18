package com.example.CortexTask.unitTests;

import com.example.CortexTask.exception.ExcursionNotFoundException;
import com.example.CortexTask.mapper.ExcursionMapper;
import com.example.CortexTask.persistence.dto.ExcursionDTO;
import com.example.CortexTask.persistence.entity.Excursion;
import com.example.CortexTask.repository.ExcursionRepository;
import com.example.CortexTask.service.serviceImpl.ExcursionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExcursionServiceImplUnitTests {

    @InjectMocks
    private ExcursionServiceImpl excursionService;

    @Mock
    private ExcursionRepository excursionRepository;

    @Mock
    private ExcursionMapper excursionMapper;

    private ExcursionDTO excursionDTO;
    private Excursion excursion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        excursionDTO = new ExcursionDTO();
        excursionDTO.setId(1L);
        excursionDTO.setName("Test Excursion");
        excursionDTO.setDescription("Test Description");
        excursionDTO.setPrice(BigDecimal.valueOf(100.00));

        excursion = new Excursion();
        excursion.setId(1L);
        excursion.setName("Test Excursion");
        excursion.setDescription("Test Description");
        excursion.setPrice(BigDecimal.valueOf(100.00));
    }

    @Test
    void getAllExcursions() {
        when(excursionRepository.findAll()).thenReturn(Arrays.asList(excursion));
        when(excursionMapper.toDto(any())).thenReturn(excursionDTO);

        var result = excursionService.getAllExcursions();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Excursion", result.get(0).getName());
    }

    @Test
    void getExcursionById_Exists() {
        when(excursionRepository.findById(1L)).thenReturn(Optional.of(excursion));
        when(excursionMapper.toDto(excursion)).thenReturn(excursionDTO);

        var result = excursionService.getExcursionById(1L);

        assertNotNull(result);
        assertEquals("Test Excursion", result.getName());
    }

    @Test
    void getExcursionById_NotFound() {
        when(excursionRepository.findById(1L)).thenReturn(Optional.empty());

        ExcursionNotFoundException exception = assertThrows(ExcursionNotFoundException.class, () -> {
            excursionService.getExcursionById(1L);
        });

        assertEquals("Excursion not found with ID: 1", exception.getMessage());
    }

    @Test
    void createExcursion() {
        when(excursionMapper.toEntity(excursionDTO)).thenReturn(excursion);
        when(excursionRepository.save(excursion)).thenReturn(excursion);
        when(excursionMapper.toDto(excursion)).thenReturn(excursionDTO);

        var result = excursionService.createExcursion(excursionDTO);

        assertNotNull(result);
        assertEquals("Test Excursion", result.getName());
        verify(excursionRepository).save(any(Excursion.class));
    }

    @Test
    void updateExcursion_Exists() {
        when(excursionRepository.findById(1L)).thenReturn(Optional.of(excursion));
        when(excursionMapper.toEntity(excursionDTO)).thenReturn(excursion);
        when(excursionRepository.save(excursion)).thenReturn(excursion);
        when(excursionMapper.toDto(excursion)).thenReturn(excursionDTO);

        var result = excursionService.updateExcursion(1L, excursionDTO);

        assertNotNull(result);
        assertEquals("Test Excursion", result.getName());
        verify(excursionRepository).save(any(Excursion.class));
    }

    @Test
    void updateExcursion_NotFound() {
        when(excursionRepository.findById(1L)).thenReturn(Optional.empty());

        ExcursionNotFoundException exception = assertThrows(ExcursionNotFoundException.class, () -> {
            excursionService.updateExcursion(1L, excursionDTO);
        });

        assertEquals("Excursion not found with ID: 1", exception.getMessage());
    }

    @Test
    void deleteExcursion_Exists() {
        when(excursionRepository.findById(1L)).thenReturn(Optional.of(excursion));

        assertDoesNotThrow(() -> excursionService.deleteExcursion(1L));
        verify(excursionRepository).deleteById(1L);
    }

    @Test
    void deleteExcursion_NotFound() {
        when(excursionRepository.findById(1L)).thenReturn(Optional.empty());

        ExcursionNotFoundException exception = assertThrows(ExcursionNotFoundException.class, () -> {
            excursionService.deleteExcursion(1L);
        });

        assertEquals("Excursion not found with ID: 1", exception.getMessage());
    }
}
