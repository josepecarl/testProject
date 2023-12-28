package com.springboot.testProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.testProject.entity.PhotoEntity;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Integer> {
}
