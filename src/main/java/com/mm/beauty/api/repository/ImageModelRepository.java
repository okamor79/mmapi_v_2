package com.mm.beauty.api.repository;

import com.mm.beauty.api.entity.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageModelRepository extends JpaRepository<ImageModel, Long> {

    Optional<ImageModel> findByUserId(Long id);
    Optional<ImageModel> findByCourseId(Long id);

}
