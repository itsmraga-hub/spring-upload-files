package com.example.springfiles.upload_files.services;

import com.example.springfiles.upload_files.entity.CustomUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    CustomUser saveDocument(MultipartFile multipartFile) throws Exception;

    void SaveDocuments(MultipartFile[] multipartFiles) throws Exception;

    List<CustomUser> getAllFiles();
}
