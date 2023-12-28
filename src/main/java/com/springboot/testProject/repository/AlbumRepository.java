package com.springboot.testProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.testProject.entity.AlbumEntity;

public interface AlbumRepository extends JpaRepository<AlbumEntity, Integer> {
}