package com.example.springfiles.upload_files.services.impl;

import com.example.springfiles.upload_files.entity.CustomUser;
import com.example.springfiles.upload_files.repository.CustomUserRepository;
import com.example.springfiles.upload_files.services.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Override
    public CustomUser saveDocument(MultipartFile multipartFile) throws Exception {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path");
            }
            CustomUser customUser = new CustomUser(fileName, multipartFile.getContentType(), multipartFile.getBytes());
            System.out.println(customUser);
            log.info("Length: ", multipartFile.getBytes().length);
            System.out.println(multipartFile.getBytes().length);
            if (multipartFile.getBytes().length > (2048 * 2048)) {
                throw new Exception("File size exceeds maximum limit.");
            }
//            CustomUser customUser = new CustomUser(fileName, multipartFile.getContentType(), multipartFile.getBytes());
            customUserRepository.save(customUser);
            return customUser;
        } catch (MaxUploadSizeExceededException e) {
            throw new MaxUploadSizeExceededException(multipartFile.getSize());
        } catch (Exception e) {
            log.info("Error: ", e);
            throw new Exception("Could not save file: " + fileName);
        }
    }

    @Override
    public void SaveDocuments(MultipartFile[] multipartFiles) throws Exception {
        Arrays.asList(multipartFiles).forEach(multipartFile -> {
            try {
                saveDocument(multipartFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<CustomUser> getAllFiles() {
        return customUserRepository.findAll();
    }
}
