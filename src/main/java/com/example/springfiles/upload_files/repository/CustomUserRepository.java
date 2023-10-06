package com.example.springfiles.upload_files.repository;

import com.example.springfiles.upload_files.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserRepository extends JpaRepository<CustomUser, String> {

}