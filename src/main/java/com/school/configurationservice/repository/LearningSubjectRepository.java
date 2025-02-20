package com.school.configurationservice.repository;



import com.school.configurationservice.model.LearningSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningSubjectRepository extends JpaRepository<LearningSubject, Long> {
}
