package com.example.springfiles.upload_files.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Builder
@Entity
@Data
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "__custom_user")
public class CustomUser {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;
    private String fileType;

    @Lob
    @Column(length = 20000000)
    private byte[] data;

    public CustomUser(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
