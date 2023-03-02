package com.mm.beauty.api.repository;

import com.mm.beauty.api.entity.Courses;
import com.mm.beauty.api.entity.Sales;
import com.mm.beauty.api.entity.User;
import com.mm.beauty.api.entity.enums.OStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sales, Long> {

    Sales findByUserAndCourseAndStatusLike(User user, Courses course, OStatus status);

    List<Sales> findAllByStatusLike(OStatus status);

    @Query("SELECT s FROM Sales s WHERE s.status=:status")
    List<Sales> findByStatusLike(@Param("status") OStatus status);

    List<Sales> findByStatus(OStatus status);

    List<Sales> findAll();

    List<Sales> findAllByUser(User user);

}
