package com.school.configurationservice.repository;

import com.school.configurationservice.model.StreamSubject;
import com.school.configurationservice.model.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreamSubjectRepository extends JpaRepository<StreamSubject, Long> {
    List<StreamSubject> findByStream(Stream stream);
}
