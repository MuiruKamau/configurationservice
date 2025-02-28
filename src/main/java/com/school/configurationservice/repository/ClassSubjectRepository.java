package com.school.configurationservice.repository;

import com.school.configurationservice.model.ClassSubject;
import com.school.configurationservice.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassSubjectRepository extends JpaRepository<ClassSubject, Long> {
    List<ClassSubject> findBySchoolClass(SchoolClass schoolClass);
}
