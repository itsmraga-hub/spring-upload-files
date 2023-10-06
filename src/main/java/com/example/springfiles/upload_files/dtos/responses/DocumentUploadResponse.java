package com.example.springfiles.upload_files.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
//@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
public class DocumentUploadResponse {
    private String fileName;

    private String downloadUrl;

    private String fileType;

    private Long fileSize;

//    public DocumentUploadResponse(String fileName, String downloadUrl, String contentType, long size) {
//    }
}
