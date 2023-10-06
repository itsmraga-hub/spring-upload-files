package com.example.springfiles.upload_files.controllers;

import com.example.springfiles.upload_files.dtos.responses.DocumentUploadResponse;
import com.example.springfiles.upload_files.entity.CustomUser;
import com.example.springfiles.upload_files.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//import com.example.uploadingfiles.storage.StorageFileNotFoundException;
//import com.example.uploadingfiles.storage.StorageService;



@RestController
@RequestMapping("api/v1/files")
public class FileUploadController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/file")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok("Successful");
    }

    @PostMapping("/upload")
    public ResponseEntity<DocumentUploadResponse> uploadDocument(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        CustomUser customUser = null;
        String downloadUrl = "";
        customUser = storageService.saveDocument(multipartFile);
        downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(("/download"))
                .path(customUser.getId())
                .toUriString();
        DocumentUploadResponse documentUploadResponse = new DocumentUploadResponse(
                customUser.getFileName(),
                downloadUrl,
                multipartFile.getContentType(),
                multipartFile.getSize()
        );
        return new ResponseEntity<>(documentUploadResponse, HttpStatus.OK);
    }

    @PostMapping("/uploads")
    public ResponseEntity<?> uploadDocuments(@RequestParam("files") MultipartFile[] multipartFiles) throws Exception {
//        List<DocumentUploadResponse> documentUploadResponses = new ArrayList<>();
        storageService.SaveDocuments(multipartFiles);
        return ResponseEntity.ok("Successfully upload all the files");
    }

//    Upload to file system
    @PostMapping("/single/file")
    public ResponseEntity<DocumentUploadResponse> handleFileUpload(@RequestParam("file") MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();

        try {
            multipartFile.transferTo(new File("D:\\IntellijIDEA\\spring-files\\uploads"));
            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(fileName)
                    .toUriString();
            DocumentUploadResponse response = new DocumentUploadResponse(fileName,
                    downloadUrl,
                    multipartFile.getContentType(),
                    multipartFile.getSize());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
