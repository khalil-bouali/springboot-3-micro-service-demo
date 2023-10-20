package com.kbouali.school;

import com.kbouali.school.client.StudentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository repository;
    private final StudentClient client;

    public void saveSchool(School school) {
        repository.save(school);
    }

    public List<School> findAllSchools() {
        return repository.findAll();
    }

    public FullSchoolResponse findSchoolWithStudents(Integer schoolId) {
        School school = repository.findById(schoolId)
                .orElse(School
                        .builder()
                        .name("NOT_FOUND")
                        .email("NOT_FOUND")
                        .build());

        List<Student> students = client.findAllStudentsBySchool(schoolId);

        return FullSchoolResponse
                .builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(students)
                .build();
    }
}
