package com.example.javawebapplication.dto.upload;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileDto {

    private List<MultipartFile> files;

}
