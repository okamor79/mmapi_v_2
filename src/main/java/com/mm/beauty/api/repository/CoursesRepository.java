package com.mm.beauty.api.repository;

import com.mm.beauty.api.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long> {

    List<Courses> findAll();

    Courses findCoursesById(Long id);

    Courses findCoursesByUniqueCode(String uCode);

}
