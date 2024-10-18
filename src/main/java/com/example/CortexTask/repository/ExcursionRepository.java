package com.example.CortexTask.repository;

import com.example.CortexTask.persistence.entity.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcursionRepository extends JpaRepository<Excursion, Long> {
}
