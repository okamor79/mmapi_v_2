package com.mm.beauty.api.repository;

import com.mm.beauty.api.entity.AvatarCourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarCoursesRepository extends JpaRepository<AvatarCourseModel, Long> {
}
