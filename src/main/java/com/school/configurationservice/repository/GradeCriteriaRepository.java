package com.school.configurationservice.repository;



import com.school.configurationservice.model.GradeCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeCriteriaRepository extends JpaRepository<GradeCriteria, Long> {
}